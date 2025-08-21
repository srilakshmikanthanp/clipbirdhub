package com.srilakshmikanthanp.clipbirdhub.common.advice

import com.srilakshmikanthanp.clipbirdhub.common.dto.ErrorMessage
import com.srilakshmikanthanp.clipbirdhub.common.exception.ForbiddenException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class Http403ForbiddenAdvice {
  @ExceptionHandler(ForbiddenException::class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  fun handleForbiddenException(e: ForbiddenException): ErrorMessage {
    return ErrorMessage(e.message, e.code)
  }
}
