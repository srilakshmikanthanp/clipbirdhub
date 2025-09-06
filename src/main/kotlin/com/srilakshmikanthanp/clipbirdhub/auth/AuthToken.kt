package com.srilakshmikanthanp.clipbirdhub.auth

import java.time.Instant

data class AuthToken(
  val issuedAt: Instant,
  var token: String,
  val expiry: Instant
)
