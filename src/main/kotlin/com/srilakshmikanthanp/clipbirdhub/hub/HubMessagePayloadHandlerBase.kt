package com.srilakshmikanthanp.clipbirdhub.hub

abstract class HubMessagePayloadHandlerBase<T: HubMessagePayload>(
  override val payloadType: Class<T>
): HubMessagePayloadHandler<T>
