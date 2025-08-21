package com.srilakshmikanthanp.clipbirdhub.auth

import com.srilakshmikanthanp.clipbirdhub.common.annotation.NullOrNotBlank
import jakarta.validation.constraints.NotNull

data class BasicAuthRequestDto(
  @field:NullOrNotBlank(message = "userName can't be empty")
  @field:NotNull(message = "userName can't be null")
  var userName: String,
  @field:NullOrNotBlank(message = "password can't be empty")
  @field:NotNull(message = "password can't be null")
  var password: String,
)
