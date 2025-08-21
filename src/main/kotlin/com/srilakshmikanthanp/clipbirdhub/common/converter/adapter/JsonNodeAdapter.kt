package com.srilakshmikanthanp.clipbirdhub.common.converter.adapter

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.*
import java.lang.reflect.Type

class JsonNodeAdapter : JsonSerializer<JsonNode>, JsonDeserializer<JsonNode> {
  private val objectMapper = ObjectMapper()

  override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): JsonNode {
    return objectMapper.readTree(json.toString())
  }

  override fun serialize(src: JsonNode?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
    return JsonParser.parseString(objectMapper.writeValueAsString(src))
  }
}
