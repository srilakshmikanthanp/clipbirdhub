package com.srilakshmikanthanp.clipbirdhub.common.extensions

import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageDevicesPayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessage
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageClipboardDispatchPayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageClipboardForwardPayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageDeviceAddedPayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageDeviceRemovedPayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageDeviceUpdatedPayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageNonceChallengeCompletedPayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageNonceChallengeRequestPayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageNonceChallengeResponsePayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessagePayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageType

val HubMessagePayload.type: HubMessageType get() = when (this) {
  is HubMessageNonceChallengeResponsePayload -> HubMessageType.NONCE_CHALLENGE_RESPONSE
  is HubMessageClipboardForwardPayload -> HubMessageType.CLIPBOARD_SEND
  is HubMessageClipboardDispatchPayload -> HubMessageType.CLIPBOARD_DELIVER
  is HubMessageDeviceAddedPayload -> HubMessageType.DEVICE_JOINED
  is HubMessageDeviceRemovedPayload -> HubMessageType.DEVICE_LEFT
  is HubMessageDeviceUpdatedPayload -> HubMessageType.DEVICE_UPDATED
  is HubMessageNonceChallengeCompletedPayload -> HubMessageType.NONCE_CHALLENGE_COMPLETED
  is HubMessageNonceChallengeRequestPayload -> HubMessageType.NONCE_CHALLENGE_REQUEST
  is HubMessageDevicesPayload -> HubMessageType.HUB_DEVICES
}

fun HubMessagePayload.toHubMessage(): HubMessage<*> {
  return HubMessage(type, this)
}
