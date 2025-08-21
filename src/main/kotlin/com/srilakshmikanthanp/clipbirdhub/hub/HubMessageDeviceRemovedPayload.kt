package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.device.DeviceResponseDto

data class HubMessageDeviceRemovedPayload(
  val device: DeviceResponseDto
) : HubMessagePayload
