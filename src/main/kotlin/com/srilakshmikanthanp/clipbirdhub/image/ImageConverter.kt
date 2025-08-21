package com.srilakshmikanthanp.clipbirdhub.image

import com.srilakshmikanthanp.clipbirdhub.common.config.MapperAppConfig
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.converter.Converter

@Mapper(config = MapperAppConfig::class)
abstract class ImageToImageResponse : Converter<Image, ImageResponseDto> {
  @Value("\${backend.url}")
  private lateinit var backendURL: String

  @Mapping(target = "url", expression = "java(toUrl(image))")
  abstract override fun convert(image: Image): ImageResponseDto

  fun toUrl(image: Image): String {
    return when (image) {
      else -> "$backendURL/images/public/${image.id}"
    }
  }
}
