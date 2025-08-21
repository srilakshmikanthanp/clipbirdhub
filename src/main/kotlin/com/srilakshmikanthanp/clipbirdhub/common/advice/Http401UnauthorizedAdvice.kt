package com.srilakshmikanthanp.clipbirdhub.common.advice

import com.srilakshmikanthanp.clipbirdhub.common.dto.Message
import com.srilakshmikanthanp.clipbirdhub.common.exception.ExpiredException
import com.srilakshmikanthanp.clipbirdhub.common.exception.UnauthorizedException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class Http401UnauthorizedAdvice {
  @ExceptionHandler(UnauthorizedException::class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  fun handleTokenExpired(e: ExpiredException): Message {
    return Message(e.message)
  }
}
