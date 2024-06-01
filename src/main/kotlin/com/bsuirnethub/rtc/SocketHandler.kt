package com.bsuirnethub.rtc

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.rtc.handler.ChatHandler
import com.bsuirnethub.rtc.dialogue.DialogueParser
import com.bsuirnethub.rtc.dialogue.chat.ChatRequest
import com.bsuirnethub.rtc.dialogue.webrtc.WebRTCRequest
import com.bsuirnethub.rtc.handler.WebRTCHandler
import com.bsuirnethub.rtc.session.UserSession
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

@Component
class SocketHandler(
    private val chatHandler: ChatHandler,
    private val webRTCHandler: WebRTCHandler,
    private val clients: ConcurrentHashMap<UserId, WebSocketSession>,
    private val sessionStates: ConcurrentHashMap<UserId, UserSession>
) : TextWebSocketHandler() {

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val userId = SessionExtractor.extractSubFromSession(session)
        clients[userId] = session
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val userId = SessionExtractor.extractSubFromSession(session)
        clients.remove(userId)
    }

    override fun handleTextMessage(session: WebSocketSession, request: TextMessage) {
        when (val parsedRequest = DialogueParser.parseRequest(request.payload)) {
            is ChatRequest -> chatHandler.handleRequest(session, parsedRequest)
            is WebRTCRequest -> webRTCHandler.handleRequest(session, parsedRequest)
            else -> session.sendMessage(TextMessage("Unknown request format"))
        }
    }
}