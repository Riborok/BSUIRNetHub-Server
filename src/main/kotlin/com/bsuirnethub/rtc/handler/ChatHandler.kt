package com.bsuirnethub.rtc.handler

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.model.Chat
import com.bsuirnethub.rtc.dialogue.DialogueParser
import com.bsuirnethub.rtc.dialogue.chat.ChatRequest
import com.bsuirnethub.rtc.dialogue.chat.ChatResponse
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
    fun handleRequest(userId: UserId, chatRequest: ChatRequest) {
        when (chatRequest) {
            is ChatRequest.Send -> handleSendRequest(userId, chatRequest)
            is ChatRequest.Mark -> handleMarkRequest(userId, chatRequest)
        }
    }

    private fun handleSendRequest(userId: UserId, chatRequest: ChatRequest.Send) {
        val message = messageService.saveMessage(userId, chatRequest.chatId, chatRequest.content)
        val chat = userChatService.incrementUnreadMessagesForRecipients(userId, chatRequest.chatId)
        val response = ChatResponse.Send(chat, message)
        sendMessagesToParticipants(chat, response)
    }

    private fun handleMarkRequest(userId: UserId, chatRequest: ChatRequest.Mark) {
        val chat = userChatService.markMessagesAsRead(userId, chatRequest.chatId, chatRequest.readMessage)
        val response = ChatResponse.Mark(chat)
        sendMessagesToParticipants(chat, response)
    }

    private fun sendMessagesToParticipants(chat: Chat, response: ChatResponse) {
        val message = TextMessage(DialogueParser.serialize(response))
        val participants = chat.userChats?.map { it.userId }
        participants?.forEach {
            clients[it]?.sendMessage(message)
        }
    }
}
