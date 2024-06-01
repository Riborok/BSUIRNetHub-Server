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
    fun handleRequest(userId: UserId, webRTCRequest: WebRTCRequest) {
        when (webRTCRequest) {
            is WebRTCRequest.STATE -> handleStateRequest(userId, webRTCRequest)
            is WebRTCRequest.OFFER -> handleOfferRequest(userId, webRTCRequest)
            is WebRTCRequest.ANSWER -> handleAnswerRequest(userId, webRTCRequest)
            is WebRTCRequest.ICE -> handleIceRequest(userId, webRTCRequest)
        }
    }

    private fun handleStateRequest(userId: UserId, webRTCRequest: WebRTCRequest.STATE) {
    }

    private fun handleOfferRequest(userId: UserId, webRTCRequest: WebRTCRequest.OFFER) {
    }

    private fun handleAnswerRequest(userId: UserId, webRTCRequest: WebRTCRequest.ANSWER) {
    }

    private fun handleIceRequest(userId: UserId, webRTCRequest: WebRTCRequest.ICE) {
    }
}