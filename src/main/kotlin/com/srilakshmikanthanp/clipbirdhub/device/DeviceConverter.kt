package com.srilakshmikanthanp.clipbirdhub.device

import com.srilakshmikanthanp.clipbirdhub.common.config.MapperAppConfig
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.springframework.core.convert.converter.Converter

@Mapper(config = MapperAppConfig::class)
interface DeviceToDeviceResponseDtoConverter : Converter<Device, DeviceResponseDto> {
  @Mapping(source = "user.id", target = "userId")
  override fun convert(device: Device): DeviceResponseDto
}
