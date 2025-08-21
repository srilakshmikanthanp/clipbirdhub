package com.srilakshmikanthanp.clipbirdhub.common.config

import com.srilakshmikanthanp.clipbirdhub.hub.HubWebSocketServer
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig(
  private val hubWebSocketServer: HubWebSocketServer
): WebSocketConfigurer {
  override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
    registry.addHandler(hubWebSocketServer, "hub")
  }
}
