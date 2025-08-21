package com.srilakshmikanthanp.clipbirdhub.device

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface DeviceService {
  fun getAllUserDevices(userId: String, search: String?, pageable: Pageable): Page<Device>
  fun isUserDevice(userId: String, deviceId: String): Boolean

  fun getById(deviceId: String): Device
  fun save(device: Device): Device
  fun deleteById(deviceId: String)
}
