package com.bsuirnethub.config

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.component.SocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.*


@Configuration
@EnableWebSocket
class WebSocketConfig : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(SocketHandler(), ApiPaths.SOCKET)
            .setAllowedOrigins("*")
    }
}