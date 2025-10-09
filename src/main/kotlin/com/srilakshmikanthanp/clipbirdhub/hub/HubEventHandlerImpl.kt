package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.common.extensions.deviceToDeviceResponseDto
import com.srilakshmikanthanp.clipbirdhub.common.extensions.toHubMessage
import com.srilakshmikanthanp.clipbirdhub.device.DeviceService
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service

@Service
class HubEventHandlerImpl(
  private val hubMessageHandler: HubMessageHandler,
  private val hubDeviceSessionRegistry: HubDeviceSessionRegistry,
  private val conversionService: ConversionService,
  private val deviceService: DeviceService
): HubEventHandler {
  override fun afterVerifiedConnectionEstablished(session: HubSession) {
    val currentDevice = deviceService.setOnline(session.getDevice().id!!, true)
    val otherDevices = hubDeviceSessionRegistry.getAllSessionsByUser(session.getUser()).map { conversionService.deviceToDeviceResponseDto(it.getDevice()) }
    val otherSessions = hubDeviceSessionRegistry.getAllSessionsByUser(session.getUser())
    hubDeviceSessionRegistry.register(session)
    val deviceAddedHubMessage = HubMessageDeviceAddedPayload(conversionService.deviceToDeviceResponseDto(currentDevice)).toHubMessage()
    otherSessions.forEach { it.sendMessage(deviceAddedHubMessage) }
    val devicesHubMessage = HubMessageDevicesPayload(otherDevices).toHubMessage()
    session.sendMessage(devicesHubMessage)
  }

  override fun handleHubMessage(session: HubSession, message: HubMessage<out HubMessagePayload>) {
    if(hubDeviceSessionRegistry.hasSession(session)) hubMessageHandler.handle(session, message)
  }

  override fun afterConnectionClosed(session: HubSession, code: Int, reason: String?) {
    if(hubDeviceSessionRegistry.hasSession(session)) hubDeviceSessionRegistry.unregister(session)
    val currentDevice = deviceService.setOnline(session.getDevice().id!!, false)
    val hubMessage = HubMessageDeviceRemovedPayload(conversionService.deviceToDeviceResponseDto(currentDevice)).toHubMessage()
    hubDeviceSessionRegistry.getAllSessionsByUser(session.getUser()).forEach { it.sendMessage(hubMessage) }
  }
}
