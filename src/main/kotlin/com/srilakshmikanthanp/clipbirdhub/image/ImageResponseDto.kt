package com.srilakshmikanthanp.clipbirdhub.image

import java.time.Instant

class ImageResponseDto(
  var id: String,
  var name: String,
  var url: String,
  var createdAt: Instant,
  var updatedAt: Instant,
)
