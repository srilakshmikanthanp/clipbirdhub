package com.srilakshmikanthanp.clipbirdhub.hub

data class HubMessageClipboardDispatchPayload(
  val fromDeviceId: String,
  val content: String,
) : HubMessagePayload
