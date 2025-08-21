package com.srilakshmikanthanp.clipbirdhub.common.converter.jakson

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class JsonNodeConverter : AttributeConverter<JsonNode, String> {
  private val mapper = ObjectMapper()

  override fun convertToDatabaseColumn(node: JsonNode): String {
    return node.toString()
  }

  override fun convertToEntityAttribute(data: String): JsonNode {
    return mapper.readTree(data)
  }
}
