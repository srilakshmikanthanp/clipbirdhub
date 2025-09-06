package com.srilakshmikanthanp.clipbirdhub.session

import java.time.Instant

data class SessionResponseDto(
  val id: String,
  val current: Boolean,
  val userAgent: String?,
  val ipAddress: String,
  val expiry: Instant,
  val userId: String,
  val createdAt: Instant,
  val updatedAt: Instant,
)
