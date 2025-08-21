package com.srilakshmikanthanp.clipbirdhub.auth

import com.srilakshmikanthanp.clipbirdhub.user.User
import com.srilakshmikanthanp.clipbirdhub.user.UserService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AppUserDetailsService(
  private val userService: UserService,
) : UserDetailsService {
  class AppUserDetails(override val user: User) : UserDetails, UserPrincipal {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
      return mutableListOf()
    }

    override fun getPassword(): String {
      return user.password
    }

    override fun getUsername(): String {
      return user.email
    }

    override fun isEnabled(): Boolean {
      return user.isActive
    }
  }

  override fun loadUserByUsername(username: String): UserDetails {
    val user = userService.getByUserNameOrEmail(username, username)
    return AppUserDetails(user)
  }
}
