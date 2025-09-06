package com.srilakshmikanthanp.clipbirdhub.session

import com.srilakshmikanthanp.clipbirdhub.common.config.MapperAppConfig
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.springframework.core.convert.converter.Converter

/**
 *package com.srilakshmikanthanp.clipbirdhub.session
 *
 * import com.srilakshmikanthanp.clipbirdhub.user.User
 * import jakarta.persistence.Column
 * import jakarta.persistence.Entity
 * import jakarta.persistence.Id
 * import jakarta.persistence.JoinColumn
 * import jakarta.persistence.ManyToOne
 * import org.hibernate.annotations.CreationTimestamp
 * import org.hibernate.annotations.UpdateTimestamp
 * import java.time.Instant
 *
 * @Entity
 * data class Session(
 *   @Column(nullable = false)
 *   @Id
 *   var id: String? = null,
 *
 *   @Column(nullable = true)
 *   var userAgent: String? = null,
 *
 *   @Column(nullable = false)
 *   var ipAddress: String,
 *
 *   @Column(columnDefinition = "TEXT", nullable = false, unique = true)
 *   var token: String,
 *
 *   @Column(nullable = false)
 *   var expiry: Instant,
 *
 *   @ManyToOne
 *   @JoinColumn(name = "user_id", nullable = false)
 *   var user: User,
 *
 *   @Column(nullable = false, updatable = false)
 *   @CreationTimestamp
 *   var createdAt: Instant? = null,
 *
 *   @Column(nullable = false)
 *   @UpdateTimestamp
 *   var updatedAt: Instant? = null,
 * )
 *
 * data class SessionResponseDto(
 *   val id: String?,
 *   val current: Boolean,
 *   val userAgent: String?,
 *   val ipAddress: String,
 *   val expiry: Instant,
 *   val userId: String,
 *   val createdAt: Instant?,
 *   val updatedAt: Instant?,
 * )
 *
 */
@Mapper(config = MapperAppConfig::class)
interface SessionMapper {
  @Mapping(source = "source.user.id", target = "userId")
  @Mapping(source = "current", target = "current")
  fun convert(source: Session, current: Boolean): SessionResponseDto
}
