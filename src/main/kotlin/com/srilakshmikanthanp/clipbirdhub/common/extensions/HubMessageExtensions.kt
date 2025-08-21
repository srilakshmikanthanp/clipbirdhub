package com.srilakshmikanthanp.clipbirdhub.common.extensions

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.srilakshmikanthanp.clipbirdhub.hub.HubMessage

fun HubMessage<*>.toJson(): String {
  return jacksonObjectMapper().writeValueAsString(this)
}
