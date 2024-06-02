package com.bsuirnethub.config

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.rtc.SocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig(
    private val socketHandler: SocketHandler
) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry
            .addHandler(socketHandler, ApiPaths.SOCKET)
            .setAllowedOrigins("*")
    }
}
