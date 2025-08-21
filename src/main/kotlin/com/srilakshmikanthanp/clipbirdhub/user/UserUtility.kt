package com.srilakshmikanthanp.clipbirdhub.user

import com.srilakshmikanthanp.clipbirdhub.auth.UserPrincipal
import org.springframework.security.core.context.SecurityContextHolder

object UserUtility {
  val currentUser: User
    get() {
      return (SecurityContextHolder.getContext().authentication.principal as UserPrincipal).user
    }
}
