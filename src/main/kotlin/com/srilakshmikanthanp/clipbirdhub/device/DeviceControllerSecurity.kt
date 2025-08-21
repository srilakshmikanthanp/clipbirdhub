package com.srilakshmikanthanp.clipbirdhub.device

interface DeviceControllerSecurity {
  fun isDeviceOwnedCurrentUser(deviceId: String): Boolean
}
