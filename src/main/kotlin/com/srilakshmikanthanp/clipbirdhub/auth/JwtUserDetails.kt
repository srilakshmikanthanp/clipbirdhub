package com.srilakshmikanthanp.clipbirdhub.auth

import com.srilakshmikanthanp.clipbirdhub.user.UserService
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

class JwtUserDetails(
  private val principal: UserPrincipal,
  val jwt: Jwt,
) : AbstractAuthenticationToken(listOf<GrantedAuthority>()) {

  class JwtUserDetailsConverter(
    private val userService: UserService,
  ) : Converter<Jwt, JwtUserDetails> {
    override fun convert(jwt: Jwt): JwtUserDetails {
      val user = userService.getByEmail(jwt.subject)
      val principal = object: UserPrincipal { override val user = user }
      return JwtUserDetails(principal ,jwt)
    }
  }

  override fun getCredentials() = jwt
  override fun getPrincipal() = principal
  override fun isAuthenticated() = true
}
