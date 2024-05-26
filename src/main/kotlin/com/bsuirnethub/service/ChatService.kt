package com.bsuirnethub.service

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.component.ChatFinder
import com.bsuirnethub.component.UserFinder
import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.model.*
import com.bsuirnethub.repository.ChatRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ChatService(
    private val userFinder: UserFinder,
    private val chatFinder: ChatFinder,
    private val chatRepository: ChatRepository,
) {
    fun createUniqueChat(participantIds: List<UserId>): Chat {
        val userEntities = userFinder.findUserEntitiesByIdsOrThrow(participantIds).toMutableSet()
        validateChatDoesNotExist(userEntities)
        var chatEntity = ChatEntity(participants = userEntities)
        chatEntity = chatRepository.save(chatEntity)
        return chatEntity.toModel()
    }

    private fun validateChatDoesNotExist(userEntities: Set<UserEntity>) {
        if (chatFinder.getExistingChatCountByParticipantEntities(userEntities) > 0)
            throw RestStatusException("Chat between users ${userEntities.map { it.userId }} already exists", HttpStatus.CONFLICT)
    }

    fun deleteChat(senderId: UserId, chatId: Long) {
        val chatEntity = chatFinder.findChatEntityByIdOrThrow(chatId)
        validateSenderIdInParticipants(senderId, chatEntity.participants.mapNotNull { it.userId })
        chatRepository.delete(chatEntity)
    }

    private fun validateSenderIdInParticipants(senderId: UserId, participantIds: List<UserId>) {
        if (!participantIds.any { it == senderId })
            throw RestStatusException("Sender with ID $senderId not found in participants", HttpStatus.BAD_REQUEST)
    }

    fun getUniqueChat(senderId: UserId, participantIds: List<UserId>): Chat {
        validateSenderIdInParticipants(senderId, participantIds)
        val userEntities = userFinder.findUserEntitiesByIdsOrThrow(participantIds).toSet()
        val chatEntity = chatFinder.findSingleChatEntityByParticipantEntitiesOrThrow(userEntities)
        return chatEntity.toModel()
    }
}