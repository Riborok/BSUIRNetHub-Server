package com.bsuirnethub.service

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.finder.ChatFinder
import com.bsuirnethub.finder.UserFinder
import com.bsuirnethub.validator.ChatValidator
import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.entity.UserChatEntity
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.model.Chat
import com.bsuirnethub.model.ChatConverter
import com.bsuirnethub.repository.ChatRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ChatService(
    private val userFinder: UserFinder,
    private val chatFinder: ChatFinder,
    private val chatRepository: ChatRepository,
    private val chatConverter: ChatConverter,
    private val chatValidator: ChatValidator,
) {
    fun createUniqueChat(participantIds: List<UserId>): Chat {
        val userEntities = userFinder.findUserEntitiesByIdsOrThrow(participantIds)
        validateChatDoesNotExist(userEntities, participantIds)
        val chatEntity = createChatEntity(userEntities)
        return chatValidator.validateParticipantsUniqueness(participantIds) {
            val savedChatEntity = chatRepository.save(chatEntity)
            chatConverter.toModel(savedChatEntity)
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
        return chatConverter.toModel(chatEntity)
    }

    fun getChat(senderId: UserId, chatId: Long): Chat {
        val chatEntity = chatFinder.findChatEntityByIdOrThrow(chatId)
        chatValidator.validateSenderIdInParticipants(senderId, chatEntity)
        return chatConverter.toModel(chatEntity)
    }

    fun getChats(userId: UserId): List<Chat?> {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        val userChatEntities = userEntity.userChats
        return userChatEntities.map { it.chat?.let { chat -> chatConverter.toModel(chat) } }
    }
}