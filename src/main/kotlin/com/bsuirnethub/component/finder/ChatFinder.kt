package com.bsuirnethub.component.finder

import com.bsuirnethub.component.validator.ChatValidator
import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.repository.ChatRepository
import org.springframework.stereotype.Component

@Component
class ChatFinder(
    private val chatRepository: ChatRepository,
    private val chatValidator: ChatValidator
) {
    fun findChatEntityByIdOrThrow(chatId: Long): ChatEntity {
        return chatValidator.validateChatExist(chatRepository.findById(chatId).orElse(null), chatId)
    }

    fun getExistingChatCountByParticipantEntities(participantEntities: List<UserEntity>): Int {
        val chatEntities = chatRepository.findByParticipants(participantEntities, participantEntities.size.toLong())
        return chatEntities.size
    }

    fun findSingleChatEntityByParticipantEntitiesOrThrow(participantEntities: List<UserEntity>): ChatEntity {
        val chatEntities = chatRepository.findByParticipants(participantEntities, participantEntities.size.toLong())
        return getSingleChatEntityOrThrow(chatEntities, participantEntities)
    }

    private fun getSingleChatEntityOrThrow(chatEntities: List<ChatEntity>, participantEntities: List<UserEntity>): ChatEntity {
        chatValidator.validateSingleChatEntity(chatEntities, participantEntities)
        return chatEntities[0]
    }
}
