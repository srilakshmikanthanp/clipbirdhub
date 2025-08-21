package com.srilakshmikanthanp.clipbirdhub.auth

import com.srilakshmikanthanp.clipbirdhub.user.User

interface UserPrincipal {
  val user: User
}
