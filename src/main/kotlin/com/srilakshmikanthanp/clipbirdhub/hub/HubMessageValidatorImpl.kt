package com.srilakshmikanthanp.clipbirdhub.hub

import org.springframework.stereotype.Service
import kotlin.reflect.full.findAnnotation

@Service
class HubMessageValidatorImpl(
  payloadValidators: List<HubMessagePayloadValidator<HubMessagePayload>>
) : HubMessageValidator {
  private val payloadValidators: Map<Class<out HubMessagePayload>, HubMessagePayloadValidator<HubMessagePayload>> = payloadValidators.filter {
    it::class.findAnnotation<HubMessageValidation>() != null
  }.associateBy {
    it.payloadType
  }

  override fun validate(session: HubSession, message: HubMessage<out HubMessagePayload>) {
    payloadValidators[message.payload::class.java]?.validate(session, message.payload)
  }
}
