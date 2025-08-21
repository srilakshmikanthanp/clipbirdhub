package com.srilakshmikanthanp.clipbirdhub.user

import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<User, String> {
  fun findByUserNameOrEmail(userName: String, email: String): Optional<User>
  fun findByEmail(email: String): Optional<User>
}
