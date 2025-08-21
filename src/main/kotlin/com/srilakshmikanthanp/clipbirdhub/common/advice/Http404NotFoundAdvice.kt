package com.srilakshmikanthanp.clipbirdhub.common.advice

import com.srilakshmikanthanp.clipbirdhub.common.dto.Message
import com.srilakshmikanthanp.clipbirdhub.common.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class Http404NotFoundAdvice {
  @ExceptionHandler(NotFoundException::class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  fun handleNotFoundException(e: NotFoundException): Message {
    return Message(e.message)
  }
}
