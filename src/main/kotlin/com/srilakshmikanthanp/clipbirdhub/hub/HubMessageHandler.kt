package com.srilakshmikanthanp.clipbirdhub.hub

interface HubMessageHandler {
  fun handle(session: HubSession, message: HubMessage<out HubMessagePayload>)
}
