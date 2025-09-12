package com.srilakshmikanthanp.clipbirdhub.hub

data class HubMessageClipboardDispatchPayload(
  val fromDeviceId: String,
  val clipboard: List<Pair<String, ByteArray>>,
) : HubMessagePayload
