package com.bsuirnethub.rtc.handler

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.rtc.SessionExtractor
import com.bsuirnethub.rtc.dialogue.DialogueParser
import com.bsuirnethub.rtc.dialogue.webrtc.WebRTCRequest
import com.bsuirnethub.rtc.dialogue.webrtc.WebRTCResponse
import com.bsuirnethub.rtc.session.SessionState
import com.bsuirnethub.rtc.session.UserSession
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Component
class WebRTCHandler(
    private val clients: ConcurrentHashMap<UserId, WebSocketSession>,
    private val sessionStates: ConcurrentHashMap<UserId, UserSession>,
) {
    fun handleRequest(session: WebSocketSession, webRTCRequest: WebRTCRequest) {
        when (webRTCRequest) {
            is WebRTCRequest.STATE -> handleStateRequest(session, webRTCRequest)
            is WebRTCRequest.OFFER -> handleOfferRequest(session, webRTCRequest)
            is WebRTCRequest.ANSWER -> handleAnswerRequest(session, webRTCRequest)
            is WebRTCRequest.ICE -> handleIceRequest(session, webRTCRequest)
        }
    }

    private fun handleStateRequest(session: WebSocketSession, webRTCRequest: WebRTCRequest.STATE) {

    }

    private fun handleOfferRequest(session: WebSocketSession, webRTCRequest: WebRTCRequest.OFFER) {
    }

    private fun handleAnswerRequest(session: WebSocketSession, webRTCRequest: WebRTCRequest.ANSWER) {
    }

    private fun handleIceRequest(session: WebSocketSession, webRTCRequest: WebRTCRequest.ICE) {
    }
}