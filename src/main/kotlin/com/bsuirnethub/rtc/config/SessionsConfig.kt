package com.bsuirnethub.rtc.config

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.rtc.session.UserSession
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ConcurrentHashMap

@Configuration
class SessionsConfig {
    @Bean
    fun sessionStates(): ConcurrentHashMap<UserId, UserSession> {
        return ConcurrentHashMap()
    }
}
