package com.srilakshmikanthanp.clipbirdhub.hub

data class HubMessageNonceChallengeRequestPayload(
  val nonce: String,
) : HubMessagePayload
