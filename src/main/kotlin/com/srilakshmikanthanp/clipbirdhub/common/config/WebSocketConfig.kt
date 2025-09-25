package com.srilakshmikanthanp.clipbirdhub.common.config

import com.srilakshmikanthanp.clipbirdhub.hub.HubWebSocketServer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean

@Configuration
@EnableWebSocket
class WebSocketConfig(
  private val hubWebSocketServer: HubWebSocketServer
): WebSocketConfigurer {
  override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
    registry.addHandler(hubWebSocketServer, "hub")
  }

  @Bean
  fun createWebSocketContainer(): ServletServerContainerFactoryBean {
    val container = ServletServerContainerFactoryBean()
    val size = 10 * 1024 * 1024 // 10 MB
    container.setMaxTextMessageBufferSize(size)
    container.setMaxBinaryMessageBufferSize(size)
    return container
  }
}
