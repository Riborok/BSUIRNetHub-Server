package com.bsuirnethub.component.validator

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.RestStatusException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class ChatValidator {
    fun validateChatDoesNotExist(chatCount: Int, userIds: List<UserId>) {
        if (chatCount > 0)
            throw RestStatusException("Chat between users $userIds already exists", HttpStatus.CONFLICT)
    }

    fun validateSenderIdInParticipants(senderId: UserId, participantIds: List<UserId>) {
        if (!participantIds.any { it == senderId })
            throw RestStatusException("Sender with ID $senderId not found in participants", HttpStatus.BAD_REQUEST)
    }

    fun validateChatExist(chatEntity: ChatEntity?, chatId: Long): ChatEntity {
        return chatEntity ?: throw RestStatusException("Chat with id $chatId not found", HttpStatus.NOT_FOUND)
    }

    fun validateSingleChatEntity(chatEntities: List<ChatEntity>, participantEntities: Set<UserEntity>) {
        if (chatEntities.isEmpty())
            throw RestStatusException("Chat between users ${participantEntities.map { it.userId }} not found", HttpStatus.NOT_FOUND)
        if (chatEntities.size > 1)
            throw RestStatusException("Multiple chats between users ${participantEntities.map { it.userId }} found", HttpStatus.CONFLICT)
    }
}