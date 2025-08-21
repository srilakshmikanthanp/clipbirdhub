package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.common.exception.ApplicationException

class HubMessageValidationException(
  message: String,
  cause: Throwable? = null
) : ApplicationException(
  message = message,
  cause = cause
)
