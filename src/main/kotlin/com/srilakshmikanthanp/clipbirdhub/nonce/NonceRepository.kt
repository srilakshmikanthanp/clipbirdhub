package com.srilakshmikanthanp.clipbirdhub.nonce

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.Instant
import java.util.Optional

interface NonceRepository: JpaRepository<Nonce, String> {
  @Modifying
  @Transactional
  @Query("delete from Nonce n where n.createdAt < :threshold")
  fun deleteOlderThan(threshold: Instant): Int
  fun findByValue(value: String): Optional<Nonce>
}
