package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.device.Device
import com.srilakshmikanthanp.clipbirdhub.user.User
import org.springframework.http.HttpHeaders

interface HubSession {
  fun getHandshakeHeaders(): HttpHeaders
  fun getDevice(): Device
  fun getUser(): User
  fun close(code: Int, reason: String? = null)
  fun sendMessage(message: HubMessage<*>)
}
