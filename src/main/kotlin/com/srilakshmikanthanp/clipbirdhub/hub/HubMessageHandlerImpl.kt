package com.srilakshmikanthanp.clipbirdhub.hub

import org.springframework.stereotype.Service
import kotlin.reflect.full.findAnnotation

@Service
class HubMessageHandlerImpl(
  private val hubMessageValidator: HubMessageValidator,
  payloadHandlers: List<HubMessagePayloadHandler<HubMessagePayload>>
) : HubMessageHandler {
  private val payloadHandlers: Map<Class<out HubMessagePayload>, HubMessagePayloadHandler<HubMessagePayload>> = payloadHandlers.filter {
    it::class.findAnnotation<HubMessageHandling>() != null
  }.associateBy {
    it.payloadType
  }

  override fun handle(session: HubSession, message: HubMessage<out HubMessagePayload>) {
    hubMessageValidator.validate(session, message)
    val handler = payloadHandlers[message.payload::class.java] ?: throw IllegalArgumentException("No handler found for payload type: ${message.payload::class.java}")
    handler.handle(session, message.payload)
  }
}
