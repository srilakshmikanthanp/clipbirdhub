package com.srilakshmikanthanp.clipbirdhub.common.extensions

import com.srilakshmikanthanp.clipbirdhub.device.Device
import com.srilakshmikanthanp.clipbirdhub.device.DeviceResponseDto
import org.springframework.core.convert.ConversionService

fun ConversionService.deviceToDeviceResponseDto(device: Device): DeviceResponseDto {
  return this.convert(device, DeviceResponseDto::class.java)!!
}
