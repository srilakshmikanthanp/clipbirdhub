package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.common.exception.BadArgumentException
import com.srilakshmikanthanp.clipbirdhub.common.exception.UnauthorizedException
import com.srilakshmikanthanp.clipbirdhub.common.extensions.*
import com.srilakshmikanthanp.clipbirdhub.device.DeviceService
import com.srilakshmikanthanp.clipbirdhub.nonce.NonceService
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class HubWebSocketServer(
  private val taskScheduler: TaskScheduler,
  private val nonceService: NonceService,
  private val hubEventHandler: HubEventHandler,
  private val deviceService: DeviceService,
) : TextWebSocketHandler() {
  private fun handle(session: WebSocketSession, payload: HubMessageNonceChallengeResponsePayload) {
    val device = nonceService.verifyAndConsumeNonce(nonceService.getNonce(payload.nonce), payload.signature)
    session.setNonceChallengeCompletedDevice(device)
    hubEventHandler.afterConnectionEstablished(session.asHubSession())
  }

  private fun handle(session: WebSocketSession, message: HubMessage<out HubMessagePayload>) {
    if (message.payload is HubMessageNonceChallengeResponsePayload) return handle(session, message.payload)
    if (session.isNonceChallengeCompleted().not()) throw UnauthorizedException("Nonce challenge not completed")
    hubEventHandler.handleHubMessage(session.asHubSession(), message)
  }

  override fun afterConnectionEstablished(session: WebSocketSession) {
    val device = deviceService.getById(session.handshakeHeaders.getFirst(DEVICE_ID_HEADER) ?: throw BadArgumentException("$DEVICE_ID_HEADER header is missing"))
    if (deviceService.isUserDevice(session.getUser().id!!, device.id!!).not()) throw UnauthorizedException("Device does not belong to the user")
    val nonce = nonceService.issueNonce(device)
    val challenge = HubMessageNonceChallengeRequestPayload(nonce.value)
    session.sendMessage(challenge.toHubMessage())
    taskScheduler.schedule(SessionCloser(session), nonce.expiry)
  }

  override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
    this.handle(session, message.toHubMessage())
  }

  override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
    if (session.isNonceChallengeCompleted()) hubEventHandler.afterConnectionClosed(session.asHubSession(), status.code, status.reason)
  }

  companion object {
    const val DEVICE_ID_HEADER = "X-device-id"
  }

  private class SessionCloser(
    private val session: WebSocketSession
  ) : Runnable {
    override fun run() {
      if (session.isNonceChallengeCompleted().not()) {
        session.close(CloseStatus.POLICY_VIOLATION.withReason("Nonce challenge not completed"))
      }
    }
  }
}
