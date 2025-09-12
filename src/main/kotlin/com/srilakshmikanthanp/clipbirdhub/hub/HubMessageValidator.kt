package com.srilakshmikanthanp.clipbirdhub.hub

import org.springframework.stereotype.Service
import kotlin.reflect.full.findAnnotation

@Service
class HubMessageValidator(
  payloadValidators: List<HubMessagePayloadValidator<HubMessagePayload>>
) {
  private val payloadValidators: Map<Class<out HubMessagePayload>, HubMessagePayloadValidator<HubMessagePayload>> = payloadValidators.filter {
    it::class.findAnnotation<HubMessageValidation>() != null
  }.associateBy {
    it.payloadType
  }

  fun validate(session: HubSession, message: HubMessage<out HubMessagePayload>) {
    payloadValidators[message.payload::class.java]?.validate(session, message.payload)
  }
}
