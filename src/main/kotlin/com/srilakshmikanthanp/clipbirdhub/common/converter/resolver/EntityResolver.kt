package com.srilakshmikanthanp.clipbirdhub.common.converter.resolver


import com.srilakshmikanthanp.clipbirdhub.common.exception.NotFoundException
import com.srilakshmikanthanp.clipbirdhub.user.User
import com.srilakshmikanthanp.clipbirdhub.user.UserRepository
import org.mapstruct.Qualifier
import org.springframework.stereotype.Component

@Qualifier
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class EntityResolver

@Qualifier
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class IdToUser

@EntityResolver
@Component
class IdToEntityResolver(
  private val userRepository: UserRepository,
) {
  @IdToUser
  fun idToUser(id: String?): User? {
    return id?.let { userRepository.findById(it).orElseThrow { NotFoundException("User with $id not found") } }
  }
}
