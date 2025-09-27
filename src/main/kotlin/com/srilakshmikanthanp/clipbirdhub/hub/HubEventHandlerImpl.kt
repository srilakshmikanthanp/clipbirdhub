package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.common.extensions.deviceToDeviceResponseDto
import com.srilakshmikanthanp.clipbirdhub.common.extensions.toHubMessage
import com.srilakshmikanthanp.clipbirdhub.device.DeviceRepository
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service

@Service
class HubEventHandlerImpl(
  private val hubMessageHandler: HubMessageHandler,
  private val hubDeviceSessionRegistry: HubDeviceSessionRegistry,
  private val conversionService: ConversionService,
  private val deviceRepository: DeviceRepository,
): HubEventHandler {
  override fun afterConnectionEstablished(session: HubSession) {
    val device = session.getDevice().apply { this.isOnline = true }
    deviceRepository.save(device)
    val deviceAddedPayload = HubMessageDeviceAddedPayload(conversionService.deviceToDeviceResponseDto(device))
    val deviceAddedHubMessage = deviceAddedPayload.toHubMessage()
    val userDevices = hubDeviceSessionRegistry.getAllSessionsByUser(session.getUser()).map { conversionService.deviceToDeviceResponseDto(it.getDevice()) }
    val devicesPayload = HubMessageDevicesPayload(userDevices)
    val devicesHubMessage = devicesPayload.toHubMessage()
    val userSessions = hubDeviceSessionRegistry.getAllSessionsByUser(session.getUser())
    userSessions.forEach { it.sendMessage(deviceAddedHubMessage) }
    hubDeviceSessionRegistry.register(session)
    session.sendMessage(devicesHubMessage)
  }

  override fun handleHubMessage(session: HubSession, message: HubMessage<out HubMessagePayload>) {
    hubMessageHandler.handle(session, message)
  }

  override fun afterConnectionClosed(session: HubSession, code: Int, reason: String?) {
    val device = session.getDevice().apply { this.isOnline = false }
    deviceRepository.save(device)
    val deviceLeftPayload = HubMessageDeviceRemovedPayload(conversionService.deviceToDeviceResponseDto(device))
    val hubMessage = deviceLeftPayload.toHubMessage()
    hubDeviceSessionRegistry.unregister(session)
    hubDeviceSessionRegistry.getAllSessionsByUser(session.getUser()).forEach { it.sendMessage(hubMessage) }
  }
}
