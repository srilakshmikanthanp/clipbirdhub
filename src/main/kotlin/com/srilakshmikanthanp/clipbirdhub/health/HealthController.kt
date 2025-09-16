package com.srilakshmikanthanp.clipbirdhub.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/health")
class HealthController {
  @GetMapping
  fun healthCheck(): HealthResponseDto {
    return HealthResponseDto(Instant.now())
  }
}
