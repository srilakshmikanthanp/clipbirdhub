package com.srilakshmikanthanp.clipbirdhub.common.config

import org.mapstruct.*
import org.mapstruct.extensions.spring.SpringMapperConfig

@MapperConfig(
  mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG,
  componentModel = "spring",
  injectionStrategy = InjectionStrategy.CONSTRUCTOR,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  builder = Builder(disableBuilder = true)
)
@SpringMapperConfig
interface MapperAppConfig
