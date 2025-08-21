package com.srilakshmikanthanp.clipbirdhub.nonce

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class NonceCleanSchedule(
  private val nonceRepository: NonceRepository
) {
  @Scheduled(fixedRate = 30 * 60 * 1000)
  fun clean() {
    nonceRepository.deleteOlderThan(Instant.now().minusSeconds(NonceConstants.loginTokenExpiryTime))
  }
}
