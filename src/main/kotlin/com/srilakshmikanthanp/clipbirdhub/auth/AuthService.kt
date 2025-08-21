package com.srilakshmikanthanp.clipbirdhub.auth

import org.springframework.security.core.Authentication

interface AuthService {
  fun createLoginJwtToken(authentication: Authentication): AuthToken
  fun forgotPassword(userName: String)
  fun resetPassword(token: String, password: String)
}
