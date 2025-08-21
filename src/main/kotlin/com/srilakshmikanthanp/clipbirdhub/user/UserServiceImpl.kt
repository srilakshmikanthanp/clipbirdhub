package com.srilakshmikanthanp.clipbirdhub.user

import com.srilakshmikanthanp.clipbirdhub.common.exception.NotFoundException
import com.srilakshmikanthanp.clipbirdhub.email.EmailService
import com.srilakshmikanthanp.clipbirdhub.email.Template
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class UserServiceImpl(
  private val passwordEncoder: PasswordEncoder,
  private val userRepository: UserRepository,
  private val emailService: EmailService,
  private val jwtEncoder: JwtEncoder,
  private val jwtDecoder: JwtDecoder,
) : UserService {
  @Value("\${jwt.token.expiry.user.verify:1800}")
  private var userVerifyTokenExpiryTime: Long = 1800L

  @Value("\${frontend.url}")
  private lateinit var frontendUrl: String

  private val userVerifyScope = "user:verify"

  override fun getUserById(id: String): User {
    val optionalUser = userRepository.findById(id)
    if(optionalUser.isEmpty) {
      throw NotFoundException("User with $id not found")
    }
    return optionalUser.get()
  }

  override fun saveUser(user: User) : User {
    return userRepository.save(user)
  }

  override fun hasUser(id: String): Boolean {
    return userRepository.existsById(id)
  }

  override fun deleteUserById(id: String) {
    if(!hasUser(id)) {
      throw NotFoundException("User with $id not found")
    }
    userRepository.deleteById(id)
  }

  override fun getByUserNameOrEmail(userName: String, email: String): User {
    val optionalUser = userRepository.findByUserNameOrEmail(userName, email)
    if(optionalUser.isEmpty) {
      throw NotFoundException("User not found")
    }
    return optionalUser.get()
  }

  override fun findByEmail(email: String): Optional<User> {
    return userRepository.findByEmail(email)
  }

  override fun getByEmail(email: String): User {
    val optionalUser = this.findByEmail(email)
    if(optionalUser.isEmpty) {
      throw NotFoundException("User with $email not found")
    }
    return optionalUser.get()
  }

  override fun updatePassword(user: User, password: String) {
    this.saveUser(user.apply { this.password = passwordEncoder.encode(password) })
  }

  override fun sendVerificationMail(user: User) {
    val now = Instant.now()
    val expiryAt = now.plusSeconds(userVerifyTokenExpiryTime)
    val claims = JwtClaimsSet.builder()
      .expiresAt(expiryAt)
      .issuer("self")
      .issuedAt(now)
      .subject(user.email)
      .claim("scope", userVerifyScope)
      .build()
    val params = JwtEncoderParameters.from(claims)
    val token = jwtEncoder.encode(params).tokenValue
    val attributes = mapOf(
      "verifyLink" to "$frontendUrl/auth/verify-email?token=$token",
      "username" to user.userName,
      "expiresIn" to TimeUnit.SECONDS.toMinutes(userVerifyTokenExpiryTime)
    )
    emailService.send(
      template = Template.VerifyEmail,
      to = user.email,
      subject = "Verify Email",
      attributes = attributes
    )
  }

  override fun verify(token: String) {
    val claims = jwtDecoder.decode(token)
    val expiry = claims.expiresAt
    if (expiry == null || Instant.now().isAfter(expiry)) {
      throw NotFoundException("Token has expired.")
    }
    if (claims.getClaimAsString("scope") != userVerifyScope) {
      throw NotFoundException("Invalid token scope.")
    }
    val user = getByEmail(claims.subject)
    user.isVerified = true
    saveUser(user)
  }
}
