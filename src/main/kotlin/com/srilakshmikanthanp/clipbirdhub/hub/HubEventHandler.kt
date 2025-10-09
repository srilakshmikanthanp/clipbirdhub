package com.srilakshmikanthanp.clipbirdhub.hub

interface HubEventHandler {
  fun afterVerifiedConnectionEstablished(session: HubSession)
  fun handleHubMessage(session: HubSession, message: HubMessage<out HubMessagePayload>)
  fun afterConnectionClosed(session: HubSession, code: Int, reason: String? = null)
}
