package com.srilakshmikanthanp.clipbirdhub.nonce

import org.springframework.beans.factory.annotation.Value
import java.util.concurrent.TimeUnit

object NonceConstants {
  @Value("\${nonce.expiry.time}")
  val loginTokenExpiryTime: Long = TimeUnit.MINUTES.toSeconds(1)
  @Value("\${nonce.signature.algorithm}")
  val signatureAlgorithm = "SHA256withRSA"
}
