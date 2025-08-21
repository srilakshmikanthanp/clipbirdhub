package com.srilakshmikanthanp.clipbirdhub.device

import com.srilakshmikanthanp.clipbirdhub.common.config.MapperAppConfig
import com.srilakshmikanthanp.clipbirdhub.user.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget

@Mapper(config = MapperAppConfig::class)
interface DeviceMapper {
  @Mapping(source = "req.publicKey", target = "publicKey")
  @Mapping(source = "req.name", target = "name")
  @Mapping(source = "req.type", target = "type")
  @Mapping(source = "online", target = "online")
  @Mapping(source = "user", target = "user")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  fun create(req: DeviceRequestDto, online: Boolean,  user: User): Device

  @Mapping(source = "publicKey", target = "publicKey")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "type", target = "type")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "online", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  fun update(req: DeviceRequestDto, @MappingTarget device: Device): Device
}
