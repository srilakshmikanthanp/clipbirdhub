package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.device.DeviceService

@HubMessageValidation
class HubMessageClipboardForwardPayloadValidator(
  private val deviceService: DeviceService
) : HubMessagePayloadValidatorBase<HubMessageClipboardForwardPayload>(HubMessageClipboardForwardPayload::class.java) {
  override fun validate(session: HubSession, payload: HubMessageClipboardForwardPayload) {
    if (!deviceService.isUserDevice(session.getUser().id!!, payload.toDeviceId)) {
      throw HubMessageValidationException("Device ${payload.toDeviceId} is not owned by user ${session.getUser().id}")
    }
  }
}
