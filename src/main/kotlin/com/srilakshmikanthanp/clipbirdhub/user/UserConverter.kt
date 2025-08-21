package com.srilakshmikanthanp.clipbirdhub.user

import com.srilakshmikanthanp.clipbirdhub.common.config.MapperAppConfig
import com.srilakshmikanthanp.clipbirdhub.common.converter.resolver.IdToEntityResolver
import org.mapstruct.BeforeMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.security.crypto.password.PasswordEncoder

@Mapper(config = MapperAppConfig::class, uses = [IdToEntityResolver::class])
abstract class UserRequestDtoToUser : Converter<UserRequestDto, User> {
  @Mapping(target = "verified", expression = "java(false)")
  @UserDeserializer
  abstract override fun convert(req: UserRequestDto): User

  @Autowired
  private lateinit var passwordEncoder: PasswordEncoder

  @BeforeMapping
  fun encodePasswordBeforeMapping(userRequestDto: UserRequestDto) {
    userRequestDto.password = passwordEncoder.encode(userRequestDto.password)
  }
}

@Mapper(config = MapperAppConfig::class)
interface UserToUserResponseDto : Converter<User, UserResponseDto> {
  abstract override fun convert(user: User): UserResponseDto
}
