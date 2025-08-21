package com.srilakshmikanthanp.clipbirdhub.nonce

import com.srilakshmikanthanp.clipbirdhub.common.exception.ExpiredException
import com.srilakshmikanthanp.clipbirdhub.common.exception.NotFoundException
import com.srilakshmikanthanp.clipbirdhub.common.exception.UnauthorizedException
import com.srilakshmikanthanp.clipbirdhub.device.Device
import com.srilakshmikanthanp.clipbirdhub.device.DeviceService
import com.srilakshmikanthanp.clipbirdhub.device.getPublicKey
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.security.Signature
import java.time.Instant
import java.util.Base64
import java.util.UUID

@Service
class NonceServiceImpl (
  @PersistenceContext private val em: EntityManager,
  private val nonceRepository: NonceRepository,
  private val deviceService: DeviceService,
  private val jwtEncoder: JwtEncoder,
  private val jwtDecoder: JwtDecoder,
): NonceService {
  override fun verifyAndConsumeNonce(nonce: Nonce, signature: String): Device {
    val jwt = jwtDecoder.decode(nonce.value)
    val expiry = jwt.expiresAt ?: throw IllegalArgumentException("Nonce has no expiry time")
    if (expiry.isBefore(Instant.now())) {
      throw ExpiredException("Nonce has expired")
    }
    val device = deviceService.getById(jwt.subject)
    val sig = Signature.getInstance(NonceConstants.signatureAlgorithm)
    sig.initVerify(device.getPublicKey())
    sig.update(nonce.value.toByteArray())
    val bytes = Base64.getDecoder().decode(signature)
    if (!sig.verify(bytes)) {
      throw UnauthorizedException("Signature verification failed")
    }
    nonceRepository.delete(nonce)
    return device
  }

  @Transactional
  override fun issueNonce(device: Device): Nonce {
    val nonceId = UUID.randomUUID().toString()
    val now = Instant.now()
    val expiry = now.plusSeconds(NonceConstants.loginTokenExpiryTime)
    val claims = JwtClaimsSet.builder()
      .issuedAt(now)
      .expiresAt(expiry)
      .issuer("self")
      .subject(device.id)
      .id(nonceId)
      .build()
    val params = JwtEncoderParameters.from(claims)
    val value = jwtEncoder.encode(params).tokenValue
    val nonce = Nonce(id = nonceId, value = value, expiry = expiry, createdAt = now)
    em.persist(nonce)
    return nonce
  }

  override fun getNonce(nonce: String): Nonce {
    return nonceRepository.findByValue(nonce).orElseThrow { NotFoundException("Nonce not found") }
  }
}
