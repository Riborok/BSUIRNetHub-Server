package com.bsuirnethub.service

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.component.finder.ChatFinder
import com.bsuirnethub.component.finder.UserFinder
import com.bsuirnethub.component.validator.ChatValidator
import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.entity.UserChatEntity
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.model.Chat
import com.bsuirnethub.model.toModel
import com.bsuirnethub.repository.ChatRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ChatService(
    private val userFinder: UserFinder,
    private val chatFinder: ChatFinder,
    private val chatRepository: ChatRepository,
    private val chatValidator: ChatValidator,
) {
    fun createUniqueChat(participantIds: List<UserId>): Chat {
        val userEntities = userFinder.findUserEntitiesByIdsOrThrow(participantIds)
        validateChatDoesNotExist(userEntities, participantIds)
        val chatEntity = createChatEntity(userEntities)
        return chatValidator.validateParticipantsUniqueness(participantIds) {
            val savedChatEntity = chatRepository.save(chatEntity)
            savedChatEntity.toModel()
        }
    }

    private fun validateChatDoesNotExist(userEntities: List<UserEntity>, participantIds: List<UserId>) {
        val chatCount = chatFinder.getExistingChatCountByParticipantEntities(userEntities)
        chatValidator.validateChatDoesNotExist(chatCount, participantIds)
    }

    private fun createChatEntity(userEntities: List<UserEntity>): ChatEntity {
        val chatEntity = ChatEntity()
        val userChatEntities = userEntities.map { UserChatEntity(user = it, chat = chatEntity) }
        chatEntity.userChats.addAll(userChatEntities)
        return chatEntity
    }

    fun deleteChat(senderId: UserId, chatId: Long) {
        val chatEntity = chatFinder.findChatEntityByIdOrThrow(chatId)
        chatValidator.validateSenderIdInParticipants(senderId, chatEntity)
        chatRepository.delete(chatEntity)
    }

    fun getUniqueChat(senderId: UserId, participantIds: List<UserId>): Chat {
        chatValidator.validateSenderIdInParticipants(senderId, participantIds)
        val userEntities = userFinder.findUserEntitiesByIdsOrThrow(participantIds)
        val chatEntity = chatFinder.findSingleChatEntityByParticipantEntitiesOrThrow(userEntities)
        return chatEntity.toModel()
    }

    fun getChats(userId: UserId): List<Chat?> {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        val userChatEntities = userEntity.userChats
        return userChatEntities.map { it.chat?.toModel() }
    }
}