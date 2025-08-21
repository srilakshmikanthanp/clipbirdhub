package com.srilakshmikanthanp.clipbirdhub.hub

data class HubMessage<T: HubMessagePayload> (
  val type: HubMessageType,
  val payload: T
)
