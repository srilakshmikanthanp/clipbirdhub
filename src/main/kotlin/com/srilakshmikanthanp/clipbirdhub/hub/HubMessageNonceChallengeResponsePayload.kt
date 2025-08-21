package com.srilakshmikanthanp.clipbirdhub.hub

data class HubMessageNonceChallengeResponsePayload(
  val signature: String,
  val nonce: String,
) : HubMessagePayload
