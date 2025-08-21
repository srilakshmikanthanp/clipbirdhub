package com.srilakshmikanthanp.clipbirdhub.auth

object AuthConstants {
  val AUTH_FREE_URLS: Map<String, String> = mapOf(
    "/auth/forgot-password" to "POST",
    "/auth/reset-password" to "POST",
    "/auth/signin" to "POST",
    "/users" to "POST"
  )
}
