package com.srilakshmikanthanp.clipbirdhub.user

import com.srilakshmikanthanp.clipbirdhub.common.config.MapperAppConfig
import com.srilakshmikanthanp.clipbirdhub.common.converter.resolver.IdToEntityResolver
import org.mapstruct.BeforeMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

@Mapper(config = MapperAppConfig::class, uses = [IdToEntityResolver::class], nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
abstract class UserMapper {
  @UserDeserializer
  abstract fun update(req: UserRequestDto, @MappingTarget user: User)

  @Autowired
  private lateinit var passwordEncoder: PasswordEncoder

  @BeforeMapping
  fun encodePasswordBeforeMapping(userRequestDto: UserRequestDto) {
    if (userRequestDto.password != null) {
      userRequestDto.password = passwordEncoder.encode(userRequestDto.password)
    }
  }
}
