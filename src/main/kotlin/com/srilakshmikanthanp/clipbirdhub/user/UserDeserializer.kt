package com.srilakshmikanthanp.clipbirdhub.user

import org.mapstruct.Mapping

@Mapping(source = "avatar", target = "avatar", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.SET_TO_NULL)
annotation class UserDeserializer
