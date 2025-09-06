package com.srilakshmikanthanp.clipbirdhub.session

import java.util.Optional

interface SessionService {
  fun findAllByUserId(userId: String): List<Session>
  fun findByToken(token: String): Optional<Session>
  fun save(session: Session): Session
  fun deleteById(id: String)
}
