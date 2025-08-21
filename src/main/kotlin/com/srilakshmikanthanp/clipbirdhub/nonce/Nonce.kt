package com.srilakshmikanthanp.clipbirdhub.nonce

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
data class Nonce(
  @Column(nullable = false)
  @Id
  var id: String? = null,

  @Column(columnDefinition = "TEXT", nullable = false)
  var value: String,

  @Column(nullable = false)
  var expiry: Instant,

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  var createdAt: Instant? = null,

  @Column(nullable = false)
  @UpdateTimestamp
  var updatedAt: Instant? = null,
)
