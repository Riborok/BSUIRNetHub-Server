package com.bsuirnethub.component

import com.bsuirnethub.alias.UserId
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class SocketHandler : TextWebSocketHandler() {
    private val sessions = HashMap<UserId, WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions[extractSubFromSession(session)] = session
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(extractSubFromSession(session))
    }

    private fun extractSubFromSession(session: WebSocketSession): UserId {
        val authentication = session.principal as JwtAuthenticationToken
        val jwt = authentication.principal as Jwt
        return jwt.claims["sub"] as UserId
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload
        sessions.filter { it.key != session.id }.forEach { it.value.sendMessage(message) }
    }
}