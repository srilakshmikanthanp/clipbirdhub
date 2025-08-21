package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.common.extensions.deviceToDeviceResponseDto
import com.srilakshmikanthanp.clipbirdhub.common.extensions.toHubMessage
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service

@Service
class HubEventHandlerImpl(
  private val hubMessageHandler: HubMessageHandler,
  private val hubDeviceSessionRegistry: HubDeviceSessionRegistry,
  private val conversionService: ConversionService,
): HubEventHandler {
  override fun afterConnectionEstablished(session: HubSession) {
    val deviceAddedPayload = HubMessageDeviceAddedPayload(conversionService.deviceToDeviceResponseDto(session.getDevice()))
    val deviceAddedHubMessage = deviceAddedPayload.toHubMessage()
    val userDevices = hubDeviceSessionRegistry.getAllSessionsByUser(session.getUser()).map { conversionService.deviceToDeviceResponseDto(session.getDevice()) }
    val devicesPayload = HubMessageDevicesPayload(userDevices)
    val devicesHubMessage = devicesPayload.toHubMessage()
    val userSessions = hubDeviceSessionRegistry.getAllSessionsByUser(session.getUser())
    hubDeviceSessionRegistry.register(session)
    userSessions.forEach { it.sendMessage(deviceAddedHubMessage) }
    session.sendMessage(devicesHubMessage)
  }

  override fun handleHubMessage(session: HubSession, message: HubMessage<out HubMessagePayload>) {
    hubMessageHandler.handle(session, message)
  }

  override fun afterConnectionClosed(session: HubSession, code: Int, reason: String?) {
    val deviceLeftPayload = HubMessageDeviceRemovedPayload(conversionService.deviceToDeviceResponseDto(session.getDevice()))
    val hubMessage = deviceLeftPayload.toHubMessage()
    hubDeviceSessionRegistry.unregister(session)
    hubDeviceSessionRegistry.getAllSessionsByUser(session.getUser()).forEach { it.sendMessage(hubMessage) }
  }
}
