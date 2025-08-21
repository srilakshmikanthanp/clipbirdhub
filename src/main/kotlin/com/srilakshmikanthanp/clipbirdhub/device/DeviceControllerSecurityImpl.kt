package com.srilakshmikanthanp.clipbirdhub.device

import com.srilakshmikanthanp.clipbirdhub.user.UserUtility
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component("deviceSecurity")
@Service
class DeviceControllerSecurityImpl(
  private val deviceService: DeviceService
) : DeviceControllerSecurity {
  override fun isDeviceOwnedCurrentUser(deviceId: String): Boolean {
    return deviceService.isUserDevice(UserUtility.currentUser.id!!, deviceId)
  }
}
