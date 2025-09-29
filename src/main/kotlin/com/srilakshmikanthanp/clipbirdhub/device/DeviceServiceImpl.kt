package com.srilakshmikanthanp.clipbirdhub.device

import com.srilakshmikanthanp.clipbirdhub.common.exception.NotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class DeviceServiceImpl(private val deviceRepository: DeviceRepository) : DeviceService {
  override fun getAllUserDevices(userId: String, search: String?, pageable: Pageable): Page<Device> {
    return if (search.isNullOrBlank()) {
      deviceRepository.findAllByUserId(userId, pageable)
    } else {
      deviceRepository.findAllByUserIdAndNameContainingIgnoreCase(userId, search, pageable)
    }
  }

  override fun isUserDevice(userId: String, deviceId: String): Boolean {
    return deviceRepository.existsByIdAndUserId(deviceId, userId)
  }

  override fun getById(deviceId: String): Device {
    return deviceRepository.findById(deviceId).orElseThrow { NotFoundException("Device with ID $deviceId not found") }
  }

  override fun save(device: Device): Device {
    return deviceRepository.save(device)
  }

  override fun deleteById(deviceId: String) {
    if (!deviceRepository.existsById(deviceId)) {
      throw NotFoundException("Device with ID $deviceId not found")
    } else {
      deviceRepository.deleteById(deviceId)
    }
  }

  @Transactional
  override fun deleteAllByUserId(userId: String) {
    deviceRepository.deleteAllByUserId(userId)
  }
}
