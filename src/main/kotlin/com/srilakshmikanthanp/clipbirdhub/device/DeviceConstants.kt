package com.srilakshmikanthanp.clipbirdhub.device

import org.springframework.beans.factory.annotation.Value

object DeviceConstants {
  @Value("\${device.key.algorithm}")
  val keyAlgorithm = "RSA"
}
