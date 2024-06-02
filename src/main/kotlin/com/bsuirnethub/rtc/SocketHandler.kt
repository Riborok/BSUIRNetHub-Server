package com.bsuirnethub.rtc

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.exception.error_code.OtherErrorCode
import com.bsuirnethub.exception.error_code_exception.ErrorCodeException
import com.bsuirnethub.exception.handler.WebSocketExceptionHandler
import com.bsuirnethub.rtc.handler.ChatHandler
import com.bsuirnethub.rtc.dialogue.DialogueParser
import com.bsuirnethub.rtc.dialogue.Request
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
    private val sessionStates: ConcurrentHashMap<UserId, UserSession>,
    private val webSocketExceptionHandler: WebSocketExceptionHandler
) : TextWebSocketHandler() {

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val userId = SessionExtractor.extractSubFromSession(session)
        clients[userId] = session
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val userId = SessionExtractor.extractSubFromSession(session)
        clients.remove(userId)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        try {
            val request = DialogueParser.parseRequest(message.payload)
            handleRequest(session, request)
        } catch (e: ErrorCodeException) {
            val errorResponse = webSocketExceptionHandler.handleErrorCodeException(e)
            session.sendMessage(TextMessage(DialogueParser.serialize(errorResponse)))
        }
    }

    private fun handleRequest(session: WebSocketSession, request: Request) {
        when (request) {
            is ChatRequest -> chatHandler.handleRequest(session, request)
            is WebRTCRequest -> webRTCHandler.handleRequest(session, request)
            else -> throw ErrorCodeException(OtherErrorCode.UNKNOWN_REQUEST, request)
        }
    }
}