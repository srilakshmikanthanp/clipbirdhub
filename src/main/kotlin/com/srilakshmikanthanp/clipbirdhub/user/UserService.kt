package com.srilakshmikanthanp.clipbirdhub.user

import java.util.*

interface UserService {
  fun getUserById(id: String): User
  fun saveUser(user: User) : User
  fun hasUser(id: String): Boolean
  fun deleteUserById(id: String)
  fun getByUserNameOrEmail(userName: String, email: String): User
  fun getByEmail(email: String): User
  fun updatePassword(user: User, password: String)
  fun findByEmail(email: String): Optional<User>
  
  fun sendVerificationMail(user: User)
  fun verify(token: String)
}
