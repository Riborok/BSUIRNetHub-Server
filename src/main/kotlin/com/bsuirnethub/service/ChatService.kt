package com.bsuirnethub.service

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.component.finder.ChatFinder
import com.bsuirnethub.component.finder.UserFinder
import com.bsuirnethub.component.validator.ChatValidator
import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.entity.UserChatEntity
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
    private val chatValidator: ChatValidator
) {
    fun createUniqueChat(participantIds: List<UserId>): Chat {
        val userEntities = userFinder.findUserEntitiesByIdsOrThrow(participantIds).toMutableSet()
        val chatCount = chatFinder.getExistingChatCountByParticipantEntities(userEntities)
        chatValidator.validateChatDoesNotExist(chatCount, participantIds)
        var chatEntity = ChatEntity()
        chatEntity.userChats.addAll(userEntities.map { UserChatEntity(user = it, chat = chatEntity) })
        chatEntity = chatRepository.save(chatEntity)
        return chatEntity.toModel()
    }

    fun deleteChat(senderId: UserId, chatId: Long) {
        val chatEntity = chatFinder.findChatEntityByIdOrThrow(chatId)
        chatValidator.validateSenderIdInParticipants(senderId, chatEntity.userChats.mapNotNull { it.user?.userId })
        chatRepository.delete(chatEntity)
    }

    fun getUniqueChat(senderId: UserId, participantIds: List<UserId>): Chat {
        chatValidator.validateSenderIdInParticipants(senderId, participantIds)
        val userEntities = userFinder.findUserEntitiesByIdsOrThrow(participantIds).toSet()
        val chatEntity = chatFinder.findSingleChatEntityByParticipantEntitiesOrThrow(userEntities)
        return chatEntity.toModel()
    }
}