package com.srilakshmikanthanp.clipbirdhub.hub

data class HubMessageClipboardForwardPayload(
  val toDeviceId: String,
  val clipboard: List<Pair<String, ByteArray>>
) : HubMessagePayload
