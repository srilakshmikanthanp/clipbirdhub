package com.srilakshmikanthanp.clipbirdhub.hub

interface HubMessagePayloadValidator<T: HubMessagePayload> {
  /**
   * The type of payload this validator is responsible for validating.
   */
  val payloadType: Class<T>

  /**
   * Validates the given payload against the rules defined in this validator.
   */
  fun validate(session: HubSession, payload: T)
}
