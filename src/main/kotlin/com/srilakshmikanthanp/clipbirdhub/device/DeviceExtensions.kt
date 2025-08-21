package com.srilakshmikanthanp.clipbirdhub.device

import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.Base64

fun Device.getPublicKey(): PublicKey {
  val bytes = Base64.getDecoder().decode(this.publicKey)
  val spec = X509EncodedKeySpec(bytes)
  val keyFactory = KeyFactory.getInstance(DeviceConstants.keyAlgorithm)
  return keyFactory.generatePublic(spec)
}
