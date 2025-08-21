package com.srilakshmikanthanp.clipbirdhub.common.config

import org.mapstruct.InjectionStrategy
import org.mapstruct.MapperConfig
import org.mapstruct.MappingInheritanceStrategy
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.extensions.spring.SpringMapperConfig

@MapperConfig(
  mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG,
  componentModel = "spring",
  injectionStrategy = InjectionStrategy.CONSTRUCTOR,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
@SpringMapperConfig
interface MapperAppConfig
