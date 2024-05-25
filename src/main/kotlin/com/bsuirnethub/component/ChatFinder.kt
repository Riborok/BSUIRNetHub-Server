package com.bsuirnethub.component

import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.repository.ChatRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class ChatFinder(
    private val chatRepository: ChatRepository
) {
    fun findChatEntityByIdOrThrow(chatId: Long): ChatEntity {
        return chatRepository.findById(chatId)
            .orElseThrow { RestStatusException("Chat with id $chatId not found", HttpStatus.NOT_FOUND) }
    }

    fun getExistingChatCountByParticipantEntities(participantEntities: Set<UserEntity>): Int {
        val chats = chatRepository.findByParticipants(participantEntities, participantEntities.size.toLong())
        return chats.size
    }

    fun findSingleChatEntityByParticipantEntitiesOrThrow(participantEntities: Set<UserEntity>): ChatEntity {
        val chats = chatRepository.findByParticipants(participantEntities, participantEntities.size.toLong())
        return getSingleChatEntityOrThrow(chats, participantEntities)
    }

    private fun getSingleChatEntityOrThrow(chatEntities: List<ChatEntity>, participantEntities: Set<UserEntity>): ChatEntity {
        if (chatEntities.isEmpty())
            throw RestStatusException("Chat between users ${participantEntities.map { it.userId }} not found", HttpStatus.NOT_FOUND)
        if (chatEntities.size > 1)
            throw RestStatusException("Multiple chats between users ${participantEntities.map { it.userId }} found", HttpStatus.CONFLICT)
        return chatEntities[0]
    }
}
