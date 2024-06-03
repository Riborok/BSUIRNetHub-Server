package com.bsuirnethub.rtc.handler

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.exception.error_code.WebRTCErrorCode
import com.bsuirnethub.exception.error_code_exception.ErrorCodeException
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

// TODO: Create normal structure and error handling
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
/*        val userId = SessionExtractor.extractSubFromSession(session)
        val recipientId = webRTCRequest.recipientId
        val clientExists = clients[recipientId] != null
        val userSession = sessionStates[userId]

        val responseState = when {
            !clientExists -> SessionState.Impossible
            userSession != null && userSession.userId == recipientId -> userSession.sessionState
            else -> SessionState.Ready
        }

        val response = WebRTCResponse.STATE(responseState, recipientId)
        val message = TextMessage(DialogueParser.serialize(response))
        session.sendMessage(message)*/

        val userId = SessionExtractor.extractSubFromSession(session)
        val recipientId = webRTCRequest.recipientId

        if (clients[recipientId] == null) {
            throw ErrorCodeException(WebRTCErrorCode.USER_NOT_CONNECTED, recipientId)
        }

        val response = WebRTCResponse.STATE(userId, webRTCRequest.sessionState)
        val message = TextMessage(DialogueParser.serialize(response))
        clients[recipientId]!!.sendMessage(message)
    }

    private fun handleOfferRequest(session: WebSocketSession, webRTCRequest: WebRTCRequest.OFFER) {
        val userId = SessionExtractor.extractSubFromSession(session)
        val recipientId = webRTCRequest.recipientId

        if (clients[recipientId] == null) {
            throw ErrorCodeException(WebRTCErrorCode.USER_NOT_CONNECTED, recipientId)
        }

/*        val userSessionState = sessionStates[userId]
        val recipientSessionState = sessionStates[recipientId]

        if (userSessionState == null || recipientSessionState == null) {
            throw ErrorCodeException(WebRTCErrorCode.SESSION_STATE_MISMATCH, null)
        }

        if (userSessionState.participantId != recipientId || recipientSessionState.participantId != userId) {
            throw ErrorCodeException(WebRTCErrorCode.PARTICIPANT_ID_MISMATCH, null)
        }

        if (userSessionState.sessionState != recipientSessionState.sessionState) {
            throw ErrorCodeException(WebRTCErrorCode.SESSION_STATE_MISMATCH, null)
        }

        if (userSessionState.sessionState != SessionState.Ready) {
            throw ErrorCodeException(WebRTCErrorCode.INVALID_SESSION_STATE, null)
        }*/

        notifyAboutStateUpdate(userId, recipientId, SessionState.Creating)
        val response = WebRTCResponse.OFFER(userId, webRTCRequest.content)
        val message = TextMessage(DialogueParser.serialize(response))
        clients[recipientId]!!.sendMessage(message)
    }

    private fun handleAnswerRequest(session: WebSocketSession, webRTCRequest: WebRTCRequest.ANSWER) {
        val userId = SessionExtractor.extractSubFromSession(session)
        val recipientId = webRTCRequest.recipientId

        if (clients[recipientId] == null) {
            throw ErrorCodeException(WebRTCErrorCode.USER_NOT_CONNECTED, recipientId)
        }

        val response = WebRTCResponse.ANSWER(userId, webRTCRequest.content)
        val message = TextMessage(DialogueParser.serialize(response))
        clients[recipientId]!!.sendMessage(message)
        notifyAboutStateUpdate(userId, recipientId, SessionState.Active)
    }

    private fun handleIceRequest(session: WebSocketSession, webRTCRequest: WebRTCRequest.ICE) {
        val userId = SessionExtractor.extractSubFromSession(session)
        val recipientId = webRTCRequest.recipientId

        if (clients[recipientId] == null) {
            throw ErrorCodeException(WebRTCErrorCode.USER_NOT_CONNECTED, recipientId)
        }

        val response = WebRTCResponse.ICE(userId, webRTCRequest.content)
        val message = TextMessage(DialogueParser.serialize(response))
        clients[recipientId]!!.sendMessage(message)
    }

    private fun notifyAboutStateUpdate(userId: UserId, recipientId: UserId, sessionState: SessionState) {
        val userSession = clients[userId]
        var response = WebRTCResponse.STATE(recipientId, sessionState)
        var message = TextMessage(DialogueParser.serialize(response))
        userSession?.sendMessage(message)

        val recipientSession = clients[recipientId]
        response = WebRTCResponse.STATE(userId, sessionState)
        message = TextMessage(DialogueParser.serialize(response))
        recipientSession?.sendMessage(message)
    }
}