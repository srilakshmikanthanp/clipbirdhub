package com.srilakshmikanthanp.clipbirdhub.common.exception

class ForbiddenException(override val message: String, val code: String) : ApplicationException(message) {
  companion object {
    const val USER_NOT_VERIFIED = "USER_NOT_VERIFIED"
  }
}
