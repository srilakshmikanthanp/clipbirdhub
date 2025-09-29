package com.srilakshmikanthanp.clipbirdhub.session

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface SessionRepository: JpaRepository<Session, String> {
  fun findAllByUserId(userId: String): List<Session>
  fun findByToken(token: String): Optional<Session>
  fun deleteAllByTokenNotAndUserId(token: String, userId: String)
}
