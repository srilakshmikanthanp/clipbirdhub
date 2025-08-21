package com.srilakshmikanthanp.clipbirdhub.user

import java.time.Instant

data class UserResponseDto(
  var id: String,
  var firstName: String,
  var lastName: String,
  var userName: String,
  var email: String,
  var password: String,
  var avatar: String?,
  var isActive: Boolean,
  var createdAt: Instant,
  var updatedAt: Instant,
)
