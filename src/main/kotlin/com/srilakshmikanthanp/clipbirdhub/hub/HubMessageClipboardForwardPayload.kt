package com.srilakshmikanthanp.clipbirdhub.hub

data class HubMessageClipboardForwardPayload(
  val toDeviceId: String,
  val content: String,
) : HubMessagePayload
