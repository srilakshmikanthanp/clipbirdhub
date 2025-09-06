package com.srilakshmikanthanp.clipbirdhub.auth

import com.srilakshmikanthanp.clipbirdhub.session.Session
import com.srilakshmikanthanp.clipbirdhub.session.SessionService
import com.srilakshmikanthanp.clipbirdhub.user.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RequestMapping("/auth")
@RestController
class AuthController (
  private val authenticationManager: AuthenticationManager,
  private val authService: AuthService,
  private val sessionService: SessionService,
  private val userService: UserService
) {
  @PostMapping("/signin")
  fun signIn(@Validated @RequestBody basicAuthRequestDto: BasicAuthRequestDto, request: HttpServletRequest): AuthToken {
    val authRequestRequest = UsernamePasswordAuthenticationToken.unauthenticated(basicAuthRequestDto.userName, basicAuthRequestDto.password)
    val user = userService.getByUserNameOrEmail(basicAuthRequestDto.userName, basicAuthRequestDto.userName)
    val authentication = authenticationManager.authenticate(authRequestRequest)
    val token = authService.createLoginJwtToken(authentication)
    val session = Session(
      userAgent = request.getHeader("User-Agent"),
      ipAddress = request.remoteAddr,
      token = token.token,
      expiry = token.expiry,
      user = user
    )
    sessionService.save(session)
    return token
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
