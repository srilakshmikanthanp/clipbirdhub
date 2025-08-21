package com.srilakshmikanthanp.clipbirdhub.common.advice

import com.srilakshmikanthanp.clipbirdhub.common.dto.Message
import com.srilakshmikanthanp.clipbirdhub.common.exception.DuplicateValueException
import com.srilakshmikanthanp.clipbirdhub.common.exception.BadActionException
import com.srilakshmikanthanp.clipbirdhub.common.exception.BadArgumentException
import org.springframework.http.HttpStatus
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class Http400BadRequestAdvice {
  @ExceptionHandler(MethodArgumentNotValidException::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun handleValidationException(ex: MethodArgumentNotValidException): List<String?> {
    return ex.bindingResult.allErrors.map { error: ObjectError ->
       error.defaultMessage
    }
  }

  @ExceptionHandler(BadArgumentException::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun handleInvalidArgumentException(ex: BadArgumentException): Message {
    return Message(ex.message)
  }

  @ExceptionHandler(DuplicateValueException::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun handleValidationException(ex: DuplicateValueException): Message {
    return Message(ex.message)
  }

  @ExceptionHandler(BadActionException::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun handleInvalidOperationException(ex: BadActionException): Message {
    return Message(ex.message)
  }
}
