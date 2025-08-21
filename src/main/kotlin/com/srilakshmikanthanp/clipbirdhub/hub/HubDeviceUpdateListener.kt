package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.common.extensions.toHubMessage
import com.srilakshmikanthanp.clipbirdhub.device.DeviceResponseDto
import com.srilakshmikanthanp.clipbirdhub.device.DeviceUpdatedEvent
import org.springframework.context.event.EventListener
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Component

@Component
class HubDeviceUpdateListener(
  private val hubDeviceSessionRegistry: HubDeviceSessionRegistry,
  private val conversionService: ConversionService
) {
  @EventListener
  fun onDeviceUpdated(event: DeviceUpdatedEvent) {
    val payload = HubMessageDeviceUpdatedPayload(conversionService.convert(event.device, DeviceResponseDto::class.java)!!)
    val message = payload.toHubMessage()
    hubDeviceSessionRegistry.getAllSessionsByUser(event.device.user).forEach { it.sendMessage(message) }
  }
}
