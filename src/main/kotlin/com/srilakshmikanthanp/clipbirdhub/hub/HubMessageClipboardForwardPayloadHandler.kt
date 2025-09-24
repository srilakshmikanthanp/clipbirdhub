package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.common.extensions.toHubMessage
import com.srilakshmikanthanp.clipbirdhub.device.DeviceService
import org.springframework.stereotype.Component

@Component
@HubMessageHandling
class HubMessageClipboardForwardPayloadHandler(
  private val hubDeviceSessionRegistry: HubDeviceSessionRegistry,
  private val deviceService: DeviceService
): HubMessagePayloadHandlerBase<HubMessageClipboardForwardPayload>(HubMessageClipboardForwardPayload::class.java)  {
  override fun handle(
    session: HubSession,
    payload: HubMessageClipboardForwardPayload
  ) {
    val fromDevice = session.getDevice()
    val dispatchPayload = HubMessageClipboardDispatchPayload(fromDevice.id!!, payload.clipboard)
    val toDevice = deviceService.getById(payload.toDeviceId)
    val targetSession = hubDeviceSessionRegistry.getSessionByDevice(toDevice)
    targetSession.sendMessage(dispatchPayload.toHubMessage())
  }
}
