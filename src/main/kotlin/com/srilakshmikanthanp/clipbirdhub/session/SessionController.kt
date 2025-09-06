package com.srilakshmikanthanp.clipbirdhub.session

import com.srilakshmikanthanp.clipbirdhub.user.UserUtility
import org.springframework.web.bind.annotation.*

@RequestMapping("/sessions")
@RestController
class SessionController(
  private val sessionService: SessionService,
  private val sessionMapper: SessionMapper,
) {
  @GetMapping
  fun getAllSessions(@RequestHeader("Authorization") authorization: String): List<SessionResponseDto> {
    val currentToken = authorization.removePrefix("Bearer ").trim()
    return sessionService.findAllByUserId(UserUtility.currentUser.id!!).map {
      sessionMapper.convert(it, it.token == currentToken)
    }
  }

  @DeleteMapping("/{id}")
  fun deleteSession(@PathVariable id: String) {
    sessionService.deleteById(id)
  }
}
