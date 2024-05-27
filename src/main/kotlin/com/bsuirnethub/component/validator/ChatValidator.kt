package com.bsuirnethub.component.validator

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.exception.error_code.ChatErrorCode
import org.springframework.stereotype.Component

@Component
class ChatValidator {
    fun validateChatDoesNotExist(chatCount: Int, userIds: List<UserId>) {
        if (chatCount > 0)
            throw RestStatusException(ChatErrorCode.CHAT_ALREADY_EXISTS, userIds)
    }

    fun validateSenderIdInParticipants(senderId: UserId, participantIds: List<UserId>) {
        if (!participantIds.any { it == senderId })
            throw RestStatusException(ChatErrorCode.SENDER_NOT_FOUND_IN_PARTICIPANTS, senderId)
    }

    fun validateChatExist(chatEntity: ChatEntity?, chatId: Long): ChatEntity {
        return chatEntity ?: throw RestStatusException(ChatErrorCode.CHAT_NOT_FOUND, chatId)
    }

    fun validateSingleChatEntity(chatEntities: List<ChatEntity>, participantEntities: Set<UserEntity>) {
        if (chatEntities.isEmpty())
            throw RestStatusException(ChatErrorCode.CHAT_NOT_FOUND, participantEntities.map { it.userId })
        if (chatEntities.size > 1)
            throw RestStatusException(ChatErrorCode.MULTIPLE_CHATS_FOUND, participantEntities.map { it.userId })
    }
}