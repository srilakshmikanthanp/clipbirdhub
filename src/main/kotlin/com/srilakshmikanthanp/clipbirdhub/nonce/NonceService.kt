package com.srilakshmikanthanp.clipbirdhub.nonce

import com.srilakshmikanthanp.clipbirdhub.device.Device

interface NonceService {
  fun verifyAndConsumeNonce(nonce: Nonce, signature: String): Device
  fun issueNonce(device: Device): Nonce
  fun getNonce(nonce: String): Nonce
}
