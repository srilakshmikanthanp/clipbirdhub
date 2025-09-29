package com.srilakshmikanthanp.clipbirdhub.session

import com.srilakshmikanthanp.clipbirdhub.common.exception.NotFoundException
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class SessionServiceImpl(
  private val sessionRepository: SessionRepository
): SessionService {
  override fun findAllByUserId(userId: String): List<Session> {
    return sessionRepository.findAllByUserId(userId)
  }

  override fun findByToken(token: String): Optional<Session> {
    return sessionRepository.findByToken(token)
  }

  override fun save(session: Session): Session {
    return sessionRepository.save(session)
  }

  override fun deleteById(id: String) {
    if (!sessionRepository.existsById(id)) {
      throw NotFoundException("Session with id $id does not exist")
    } else {
      sessionRepository.deleteById(id)
    }
  }

  override fun deleteAllExceptTokenByUserId(token: String, userId: String) {
    sessionRepository.deleteAllByTokenNotAndUserId(token, userId)
  }
}
