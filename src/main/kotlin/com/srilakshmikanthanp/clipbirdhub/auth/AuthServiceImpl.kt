package com.srilakshmikanthanp.clipbirdhub.auth

import com.srilakshmikanthanp.clipbirdhub.common.exception.BadArgumentException
import com.srilakshmikanthanp.clipbirdhub.common.exception.ExpiredException
import com.srilakshmikanthanp.clipbirdhub.email.EmailService
import com.srilakshmikanthanp.clipbirdhub.email.Template
import com.srilakshmikanthanp.clipbirdhub.user.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.concurrent.TimeUnit

@Service
class AuthServiceImpl(
  private val userService: UserService,
  private val jwtEncoder: JwtEncoder,
  private val jwtDecoder: JwtDecoder,
  private val emailService: EmailService,
): AuthService {
  @Value("\${jwt.token.expiry.reset-password}")
  private var resetPasswordTokenExpiryTime: Long = 1800L

  @Value("\${jwt.token.expiry.login}")
  private var loginTokenExpiryTime: Long = 36000L

  @Value("\${frontend.url}")
  private lateinit var frontendUrl: String

  private val passwordResetScope = "password:reset"

  override fun createLoginJwtToken(authentication: Authentication): AuthToken {
    val now = Instant.now()
    val claims = JwtClaimsSet.builder()
      .issuer("self")
      .issuedAt(now)
      .expiresAt(now.plusSeconds(loginTokenExpiryTime))
      .subject(authentication.name)
      .build()
    val params = JwtEncoderParameters.from(claims)
    return AuthToken(token = jwtEncoder.encode(params).tokenValue)
  }

  override fun forgotPassword(userName: String) {
    val user = userService.getByUserNameOrEmail(userName, userName)
    val now = Instant.now()
    val expiryAt = now.plusSeconds(resetPasswordTokenExpiryTime)
    val claims = JwtClaimsSet.builder()
      .issuer("self")
      .issuedAt(now)
      .expiresAt(expiryAt)
      .subject(user.email)
      .claim("scope", passwordResetScope)
      .build()
    val params = JwtEncoderParameters.from(claims)
    val token = jwtEncoder.encode(params).tokenValue
    val attributes = mapOf(
      "resetLink" to "$frontendUrl/auth/reset-password?token=$token",
      "username" to user.userName,
      "expiresIn" to TimeUnit.SECONDS.toMinutes(resetPasswordTokenExpiryTime)
    )
    emailService.send(
      template = Template.ForgotPassword,
      to = user.email,
      subject = "Forgot Password",
      attributes = attributes
    )
  }

  override fun resetPassword(token: String, password: String) {
    val claims = jwtDecoder.decode(token)
    val expiry = claims.expiresAt
    if (expiry == null || Instant.now().isAfter(expiry)) {
      throw ExpiredException("Token has expired.")
    }
    if (claims.getClaimAsString("scope") != passwordResetScope) {
      throw BadArgumentException("Invalid token scope.")
    }
    val user = userService.getByEmail(claims.subject)
    userService.updatePassword(user, password)
  }
}
