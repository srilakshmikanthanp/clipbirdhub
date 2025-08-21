package com.srilakshmikanthanp.clipbirdhub.hub

abstract class HubMessagePayloadValidatorBase<T: HubMessagePayload>(
  override val payloadType: Class<T>
): HubMessagePayloadValidator<T>
