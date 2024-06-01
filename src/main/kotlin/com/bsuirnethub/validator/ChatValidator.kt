package com.bsuirnethub.validator

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.rest_status_exception.RestStatusException
import com.bsuirnethub.exception.error_code.rest.ChatRestErrorCode
import com.bsuirnethub.model.Chat
import org.springframework.stereotype.Component

@Component
class ChatValidator {
    fun validateChatDoesNotExist(chatCount: Int, userIds: List<UserId>) {
        if (chatCount > 0)
            throw RestStatusException(ChatRestErrorCode.CHAT_ALREADY_EXISTS, userIds)
    }

    fun validateSenderIdInParticipants(senderId: UserId, chatEntity: ChatEntity) {
        validateSenderIdInParticipants(senderId, chatEntity.userChats.mapNotNull { it.user?.userId })
    }

    fun validateSenderIdInParticipants(senderId: UserId, participantIds: List<UserId>) {
        if (senderId !in participantIds)
            throw RestStatusException(ChatRestErrorCode.SENDER_NOT_FOUND_IN_PARTICIPANTS, senderId)
    }

    fun validateChatExist(chatEntity: ChatEntity?, chatId: Long): ChatEntity {
        return chatEntity ?: throw RestStatusException(ChatRestErrorCode.CHAT_NOT_FOUND, chatId)
    }

    fun validateSingleChatEntity(chatEntities: List<ChatEntity>, participantEntities: List<UserEntity>) {
        if (chatEntities.isEmpty())
            throw RestStatusException(ChatRestErrorCode.CHAT_NOT_FOUND, participantEntities.map { it.userId })
        if (chatEntities.size > 1)
            throw RestStatusException(ChatRestErrorCode.MULTIPLE_CHATS_FOUND, participantEntities.map { it.userId })
    }

    fun validateParticipantsUniqueness(participantIds: List<UserId>, operation: () -> Chat): Chat {
        return validateEntityDoesNotExists(operation, ChatRestErrorCode.DUPLICATE_PARTICIPANTS, participantIds)
    }
}