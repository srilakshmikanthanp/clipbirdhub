package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.common.extensions.getNonceChallengeCompletedDeviceOrThrow
import com.srilakshmikanthanp.clipbirdhub.common.extensions.getUser
import com.srilakshmikanthanp.clipbirdhub.common.extensions.sendMessage
import com.srilakshmikanthanp.clipbirdhub.device.Device
import com.srilakshmikanthanp.clipbirdhub.user.User
import org.springframework.http.HttpHeaders
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketSession

class HubSessionImpl(
  private val session: WebSocketSession
) : HubSession {
  override fun sendMessage(message: HubMessage<*>) {
    session.sendMessage(message)
  }

  override fun getUser(): User {
    return session.getUser()
  }

  override fun getHandshakeHeaders(): HttpHeaders {
    return session.handshakeHeaders
  }

  override fun close(code: Int, reason: String?) {
    session.close(CloseStatus(code, reason))
  }

  override fun getDevice(): Device {
    return session.getNonceChallengeCompletedDeviceOrThrow()
  }
}
