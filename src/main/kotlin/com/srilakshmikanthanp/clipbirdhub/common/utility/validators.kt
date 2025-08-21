package com.srilakshmikanthanp.clipbirdhub.common.utility

import java.time.Instant

fun validateDate(value: String): Boolean {
  return try {
    Instant.parse(value)
    true
  } catch (e: Exception) {
    false
  }
}
