package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.device.DeviceResponseDto

data class HubMessageDeviceUpdatedPayload(
  val device: DeviceResponseDto
) : HubMessagePayload
