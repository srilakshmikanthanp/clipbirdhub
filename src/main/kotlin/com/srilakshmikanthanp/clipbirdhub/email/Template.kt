package com.srilakshmikanthanp.clipbirdhub.email

enum class Template(val file: String) {
  ForgotPassword("user/forgot-password"),
  VerifyEmail("user/verify-email"),
}
