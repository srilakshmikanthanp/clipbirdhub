package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.device.DeviceResponseDto

data class HubMessageDeviceAddedPayload(
  val device: DeviceResponseDto
) : HubMessagePayload
