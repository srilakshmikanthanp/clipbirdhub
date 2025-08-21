package com.srilakshmikanthanp.clipbirdhub.common.extensions

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessage
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageClipboardDispatchPayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageClipboardForwardPayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageDeviceAddedPayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageDeviceRemovedPayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageDeviceUpdatedPayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageNonceChallengeRequestPayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageNonceChallengeResponsePayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessagePayload
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessageType
import jakarta.validation.Validation
import org.springframework.web.socket.TextMessage

fun TextMessage.toHubMessage(): HubMessage<HubMessagePayload> {
  val objectMapper = jacksonObjectMapper()
  val rootNode = objectMapper.readTree(this.payload)
  val typeNode = rootNode.get("type").takeIf { it != null && it.isTextual } ?: throw IllegalArgumentException("Invalid JSON: 'type' field is missing or not a string")
  val type = HubMessageType.valueOf(typeNode.asText())
  val payloadNode = rootNode.get("payload")

  val payload = when (type) {
    HubMessageType.NONCE_CHALLENGE_RESPONSE -> objectMapper.treeToValue(payloadNode, HubMessageNonceChallengeResponsePayload::class.java)
    HubMessageType.CLIPBOARD_SEND -> objectMapper.treeToValue(payloadNode, HubMessageClipboardForwardPayload::class.java)
    HubMessageType.CLIPBOARD_DELIVER -> objectMapper.treeToValue(payloadNode, HubMessageClipboardDispatchPayload::class.java)
    HubMessageType.DEVICE_JOINED -> objectMapper.treeToValue(payloadNode, HubMessageDeviceAddedPayload::class.java)
    HubMessageType.DEVICE_LEFT -> objectMapper.treeToValue(payloadNode, HubMessageDeviceRemovedPayload::class.java)
    HubMessageType.DEVICE_UPDATED -> objectMapper.treeToValue(payloadNode, HubMessageDeviceUpdatedPayload::class.java)
    HubMessageType.NONCE_CHALLENGE_REQUEST -> objectMapper.treeToValue(payloadNode, HubMessageNonceChallengeRequestPayload::class.java)
    HubMessageType.HUB_DEVICES -> objectMapper.treeToValue(payloadNode, HubMessagePayload::class.java) // Assuming a generic payload for this type
  }

  val validator = Validation.buildDefaultValidatorFactory().validator
  val violations = validator.validate(payload)

  if (violations.isNotEmpty()) {
    throw IllegalArgumentException("Invalid payload for type $type: ${violations.joinToString(", ") { it.message }}")
  }

  return HubMessage(type, payload)
}
