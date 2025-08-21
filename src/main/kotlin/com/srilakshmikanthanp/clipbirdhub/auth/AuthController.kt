package com.srilakshmikanthanp.clipbirdhub.auth

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RequestMapping("/auth")
@RestController
class AuthController (
  private val authenticationManager: AuthenticationManager,
  private val authService: AuthService
) {
  @PostMapping("/signin")
  fun signin(@Validated @RequestBody basicAuthRequestDto: BasicAuthRequestDto): AuthToken {
    val authRequestRequest = UsernamePasswordAuthenticationToken.unauthenticated(
      basicAuthRequestDto.userName, basicAuthRequestDto.password
    )
    val authentication = authenticationManager.authenticate(authRequestRequest)
    return authService.createLoginJwtToken(authentication)
  }

  @PostMapping("/forgot-password/{userName}")
  fun forgotPassword(@PathVariable userName: String) {
    authService.forgotPassword(userName)
  }

  @PostMapping("/reset-password/{token}")
  fun resetPassword(@PathVariable token: String, @RequestBody body: ResetPasswordRequest) {
    authService.resetPassword(token, body.password)
  }
}
