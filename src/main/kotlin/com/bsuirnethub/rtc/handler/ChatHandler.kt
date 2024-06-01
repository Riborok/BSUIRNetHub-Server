package com.bsuirnethub.rtc.handler

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.rtc.SessionExtractor
import com.bsuirnethub.rtc.dialogue.chat.ChatRequest
import com.bsuirnethub.service.MessageService
import com.bsuirnethub.service.UserChatService
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Component
class ChatHandler(
    private val userChatService: UserChatService,
    private val messageService: MessageService,
    private val clients: ConcurrentHashMap<UserId, WebSocketSession>,
) {
    fun handleRequest(session: WebSocketSession, chatRequest: ChatRequest) {
        when (chatRequest) {
            is ChatRequest.SendChatRequest -> handleSendRequest(session, chatRequest)
            is ChatRequest.MarkChatRequest -> handleMarkRequest(session, chatRequest)
        }
    }

    fun handleSendRequest(session: WebSocketSession, chatRequest: ChatRequest.SendChatRequest) {
        val userId = SessionExtractor.extractSubFromSession(session)
        val message = messageService.saveMessage(userId, chatRequest.chatId, chatRequest.content)
        val chat = userChatService.incrementUnreadMessagesForRecipients(userId, chatRequest.chatId)
        val participant = chat.userChats?.map { it.userId }
        participant?.forEach {

        }
        session.sendMessage(TextMessage("SendChatRequest"))
    }

    fun handleMarkRequest(session: WebSocketSession, chatRequest: ChatRequest.MarkChatRequest) {
        session.sendMessage(TextMessage("MarkChatRequest"))
    }
}
