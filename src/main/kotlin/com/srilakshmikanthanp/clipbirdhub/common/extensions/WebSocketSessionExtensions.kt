package com.srilakshmikanthanp.clipbirdhub.common.extensions

import com.srilakshmikanthanp.clipbirdhub.auth.JwtUserDetails
import com.srilakshmikanthanp.clipbirdhub.auth.UserPrincipal
import com.srilakshmikanthanp.clipbirdhub.device.Device
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessage
import com.srilakshmikanthanp.clipbirdhub.hub.HubSession
import com.srilakshmikanthanp.clipbirdhub.hub.HubSessionImpl
import com.srilakshmikanthanp.clipbirdhub.user.User
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession

private enum class WebSocketSessionAttributeKey(val key: String) {
  DEVICE("DEVICE"),
}

fun WebSocketSession.getNonceChallengeCompletedDevice(): Device? {
  return this.attributes[WebSocketSessionAttributeKey.DEVICE.key] as? Device
}

fun WebSocketSession.setNonceChallengeCompletedDevice(device: Device) {
  this.attributes[WebSocketSessionAttributeKey.DEVICE.key] = device
}

fun WebSocketSession.getNonceChallengeCompletedDeviceOrThrow(): Device {
  return this.getNonceChallengeCompletedDevice() ?: throw IllegalStateException("Device not set in session")
}

fun WebSocketSession.isNonceChallengeCompleted(): Boolean {
  return this.attributes.containsKey(WebSocketSessionAttributeKey.DEVICE.key)
}

fun WebSocketSession.getUser(): User {
  return this.principal?.let { it as JwtUserDetails }?.principal?.user ?: throw IllegalStateException("User not set in session principal")
}

fun WebSocketSession.sendMessage(message: HubMessage<*>) {
  this.sendMessage(TextMessage(message.toJson()))
}

fun WebSocketSession.asHubSession(): HubSession {
  return HubSessionImpl(this)
}
