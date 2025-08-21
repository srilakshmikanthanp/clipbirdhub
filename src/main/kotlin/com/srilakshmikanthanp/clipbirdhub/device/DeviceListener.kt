package com.srilakshmikanthanp.clipbirdhub.device

import jakarta.persistence.PostUpdate
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class DeviceListener(
  private val applicationEventPublisher: ApplicationEventPublisher
) {
  @PostUpdate
  fun postUpdate(device: Device) {
    applicationEventPublisher.publishEvent(DeviceUpdatedEvent(device))
  }
}
