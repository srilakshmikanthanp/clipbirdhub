package com.srilakshmikanthanp.clipbirdhub.device

data class DeviceResponseDto(
  var id: String,
  var name: String,
  var type: DeviceType,
  var publicKey: String,
  var isOnline: Boolean,
  var userId: String,
  var createdAt: String,
  var updatedAt: String,
)
