package com.srilakshmikanthanp.clipbirdhub.hub

import org.springframework.stereotype.Service
import kotlin.reflect.full.findAnnotation

@Service
class HubMessageValidator(
  payloadValidators: List<HubMessagePayloadValidator<*>>
) {
  private val payloadValidators: Map<Class<out HubMessagePayload>, HubMessagePayloadValidator<out HubMessagePayload>> = payloadValidators.filter {
    it::class.findAnnotation<HubMessageValidation>() != null
  }.associateBy {
    it.payloadType
  }

  fun validate(session: HubSession, message: HubMessage<out HubMessagePayload>) {
    val validator = payloadValidators[message.payload::class.java] as? HubMessagePayloadValidator<HubMessagePayload> ?: throw IllegalArgumentException("No validator found for payload type: ${message.payload::class.java}")
    validator.validate(session, message.payload)
  }
}
