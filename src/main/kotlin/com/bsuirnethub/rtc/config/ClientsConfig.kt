package com.bsuirnethub.rtc.config

import com.bsuirnethub.alias.UserId
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Configuration
class ClientsConfig {
    @Bean
    fun clients(): ConcurrentHashMap<UserId, WebSocketSession> {
        return ConcurrentHashMap()
    }
}
