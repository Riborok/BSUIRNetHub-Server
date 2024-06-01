package com.bsuirnethub.rtc.handler

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.rtc.SessionExtractor
import com.bsuirnethub.rtc.dialogue.webrtc.WebRTCRequest
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Component
class WebRTCHandler(
    private val clients: ConcurrentHashMap<UserId, WebSocketSession>
) {
    fun handleRequest(session: WebSocketSession, webRTCRequest: WebRTCRequest) {
        val userId = SessionExtractor.extractSubFromSession(session)
        when (webRTCRequest) {
            is WebRTCRequest.STATE -> handleStateRequest(session, webRTCRequest)
            is WebRTCRequest.OFFER -> handleOfferRequest(session, webRTCRequest)
            is WebRTCRequest.ANSWER -> handleAnswerRequest(session, webRTCRequest)
            is WebRTCRequest.ICE -> handleIceRequest(session, webRTCRequest)
        }
    }

    fun handleStateRequest(session: WebSocketSession, webRTCRequest: WebRTCRequest.STATE) {
        session.sendMessage(TextMessage("STATE"))
    }

    fun handleOfferRequest(session: WebSocketSession, webRTCRequest: WebRTCRequest.OFFER) {
        session.sendMessage(TextMessage("OFFER"))
    }

    fun handleAnswerRequest(session: WebSocketSession, webRTCRequest: WebRTCRequest.ANSWER) {
        session.sendMessage(TextMessage("ANSWER"))
    }

    fun handleIceRequest(session: WebSocketSession, webRTCRequest: WebRTCRequest.ICE) {
        session.sendMessage(TextMessage("ICE"))
    }
}