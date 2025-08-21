package com.srilakshmikanthanp.clipbirdhub.hub

interface HubMessageValidator {
  fun validate(session: HubSession, message: HubMessage<out HubMessagePayload>)
}
