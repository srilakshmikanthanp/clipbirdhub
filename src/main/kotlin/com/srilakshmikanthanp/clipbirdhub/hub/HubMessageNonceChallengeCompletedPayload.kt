package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.device.DeviceResponseDto

data class HubMessageNonceChallengeCompletedPayload(
  val device: DeviceResponseDto
): HubMessagePayload
