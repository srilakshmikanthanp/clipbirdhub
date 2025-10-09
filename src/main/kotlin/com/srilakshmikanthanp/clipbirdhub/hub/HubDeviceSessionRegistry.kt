package com.srilakshmikanthanp.clipbirdhub.hub

import com.srilakshmikanthanp.clipbirdhub.device.Device
import com.srilakshmikanthanp.clipbirdhub.user.User

interface HubDeviceSessionRegistry {
  fun register(session: HubSession)
  fun getAllSessionsByUser(user: User): List<HubSession>
  fun getSessionByDevice(device: Device): HubSession
  fun unregister(session: HubSession)
  fun hasSession(session: HubSession): Boolean
}
