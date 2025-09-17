package com.srilakshmikanthanp.clipbirdhub.device

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceRepository: JpaRepository<Device, String> {
  fun findAllByUserIdAndNameContainingIgnoreCase(userId: String, name: String?, pageable: Pageable): Page<Device>
  fun existsByIdAndUserId(deviceId: String, userId: String): Boolean
  fun findAllByUserId(userId: String, pageable: Pageable): Page<Device>
}
