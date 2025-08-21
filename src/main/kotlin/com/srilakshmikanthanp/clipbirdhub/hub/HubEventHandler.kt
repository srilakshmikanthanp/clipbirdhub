package com.srilakshmikanthanp.clipbirdhub.hub

interface HubEventHandler {
  fun afterConnectionEstablished(session: HubSession)
  fun handleHubMessage(session: HubSession, message: HubMessage<out HubMessagePayload>)
  fun afterConnectionClosed(session: HubSession, code: Int, reason: String? = null)
}
