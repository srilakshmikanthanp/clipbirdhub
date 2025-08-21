package com.srilakshmikanthanp.clipbirdhub.image

import com.srilakshmikanthanp.clipbirdhub.user.User
import jakarta.persistence.*
import org.hibernate.annotations.*
import java.sql.Types
import java.time.Instant

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class Image (
  @Column(nullable = false)
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  var id: String? = null,

  @Column(nullable = false)
  var name: String,

  @JdbcTypeCode(Types.VARBINARY)
  @Column(nullable = false, columnDefinition = "bytea")
  var blob: ByteArray,

  @ManyToOne
  @JoinColumn(name = "uploaded_by_user_id", nullable = true)
  @OnDelete(action = OnDeleteAction.SET_NULL)
  var uploadedBy: User?,

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  var createdAt: Instant? = null,

  @Column(nullable = false)
  @UpdateTimestamp
  var updatedAt: Instant? = null,
)
