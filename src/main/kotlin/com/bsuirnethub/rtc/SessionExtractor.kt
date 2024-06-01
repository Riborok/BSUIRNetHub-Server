package com.bsuirnethub.rtc

import com.bsuirnethub.alias.UserId
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.socket.WebSocketSession

object SessionExtractor {
    fun extractSubFromSession(session: WebSocketSession): UserId {
        val authentication = session.principal as JwtAuthenticationToken
        val jwt = authentication.principal as Jwt
        return jwt.claims["sub"] as UserId
    }
}