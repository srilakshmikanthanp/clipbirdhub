package com.srilakshmikanthanp.clipbirdhub.device

import org.bouncycastle.util.io.pem.PemReader
import java.io.StringReader
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec

fun Device.getPublicKey(): PublicKey {
  PemReader(StringReader(this.publicKey)).use { reader ->
    val content = reader.readPemObject().content
    val spec = X509EncodedKeySpec(content)
    val keyFactory = KeyFactory.getInstance(DeviceConstants.keyAlgorithm)
    return keyFactory.generatePublic(spec)
  }
}
