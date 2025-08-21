package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.device.Device
import com.srilakshmikanthanp.clipbirdhub.user.User
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class HubDeviceSessionRegistryImpl: HubDeviceSessionRegistry {
  private val sessionsByDevice: MutableMap<Device, HubSession> = ConcurrentHashMap()

  override fun register(session: HubSession) {
    if (sessionsByDevice.containsKey(session.getDevice())) {
      throw IllegalArgumentException("Session for device ${session.getDevice()} is already registered")
    } else {
      sessionsByDevice[session.getDevice()] = session
    }
  }

  override fun getAllSessionsByUser(user: User): List<HubSession> {
    return sessionsByDevice.values.filter { it.getUser() == user }
  }

  override fun getSessionByDevice(device: Device): HubSession {
    return sessionsByDevice[device] ?: throw IllegalArgumentException("No session registered for device ${device.id}")
  }

  override fun unregister(session: HubSession) {
    if (!sessionsByDevice.containsKey(session.getDevice())) {
      throw IllegalArgumentException("Session for device ${session.getDevice()} is not registered")
    } else {
      sessionsByDevice.remove(session.getDevice())
    }
  }
}
