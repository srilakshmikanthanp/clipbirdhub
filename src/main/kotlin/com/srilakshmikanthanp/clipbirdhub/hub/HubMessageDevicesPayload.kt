package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.device.DeviceResponseDto

data class HubMessageDevicesPayload(
  val devices: List<DeviceResponseDto>
) : HubMessagePayload
