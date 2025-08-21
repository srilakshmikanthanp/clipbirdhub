package com.srilakshmikanthanp.clipbirdhub.hub

interface HubMessagePayloadHandler<T: HubMessagePayload> {
  /**
   * The type of payload this handler is responsible for processing.
   */
  val payloadType: Class<T>

  /**
   * Handles the given payload by processing it according to the rules defined in this handler.
   */
  fun handle(session: HubSession, payload: T)
}
