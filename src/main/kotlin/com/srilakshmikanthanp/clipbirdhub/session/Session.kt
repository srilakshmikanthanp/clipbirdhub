package com.srilakshmikanthanp.clipbirdhub.session

import com.srilakshmikanthanp.clipbirdhub.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
data class Session(
  @Column(nullable = false)
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  var id: String? = null,

  @Column(nullable = true)
  var userAgent: String? = null,

  @Column(nullable = false)
  var ipAddress: String,

  @Column(columnDefinition = "TEXT", nullable = false, unique = true)
  var token: String,

  @Column(nullable = false)
  var expiry: Instant,

  @ManyToOne
  @OnDelete(OnDeleteAction.CASCADE)
  @JoinColumn(name = "user_id", nullable = false)
  var user: User,

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  var createdAt: Instant? = null,

  @Column(nullable = false)
  @UpdateTimestamp
  var updatedAt: Instant? = null,
)
