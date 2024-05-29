package com.bsuirnethub.service

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.component.finder.ChatFinder
import com.bsuirnethub.component.finder.UserChatFinder
import com.bsuirnethub.component.finder.UserFinder
import com.bsuirnethub.component.validator.ChatValidator
import com.bsuirnethub.component.validator.UserChatValidator
import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.entity.UserChatEntity
import com.bsuirnethub.model.Chat
import com.bsuirnethub.model.UserChat
import com.bsuirnethub.model.toModel
import com.bsuirnethub.repository.ChatRepository
import com.bsuirnethub.repository.UserChatRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserChatService(
    private val userFinder: UserFinder,
    private val chatFinder: ChatFinder,
    private val userChatFinder: UserChatFinder,
    private val userChatRepository: UserChatRepository,
    private val chatRepository: ChatRepository,
    private val chatValidator: ChatValidator,
    private val userChatValidator: UserChatValidator
) {
    fun incrementUnreadMessagesForRecipients(senderId: UserId, chatId: Long, messageCount: Int = 1): Chat {
        userChatValidator.validateMessageCountNonNegative(messageCount)
        val chatEntity = chatFinder.findChatEntityByIdOrThrow(chatId)
        chatValidator.validateSenderIdInParticipants(senderId, chatEntity)
        incrementUnreadMessagesExcludingSender(chatEntity, senderId, messageCount)
        val savedChatEntity = chatRepository.save(chatEntity)
        return savedChatEntity.toModel()
    }

    private fun incrementUnreadMessagesExcludingSender(chatEntity: ChatEntity, senderId: UserId, messageCount: Int) {
        chatEntity.userChats
            .filter { it.user?.userId != senderId }
            .forEach { it.unreadMessages += messageCount }
    }

    fun markMessagesAsRead(senderId: UserId, chatId: Long, messageCount: Int): UserChat {
        userChatValidator.validateMessageCountNonNegative(messageCount)
        val userChatEntity = findUserChatEntity(senderId, chatId)
        userChatEntity.unreadMessages -= messageCount.coerceAtMost(userChatEntity.unreadMessages)
        val savedUserChatEntity = userChatRepository.save(userChatEntity)
        return savedUserChatEntity.toModel()
    }

    private fun findUserChatEntity(senderId: UserId, chatId: Long): UserChatEntity {
        val senderEntity = userFinder.findUserEntityByIdOrThrow(senderId)
        val chatEntity = chatFinder.findChatEntityByIdOrThrow(chatId)
        val userChatEntity = userChatFinder.findUserChatEntityByUserEntityAndChatEntityOrThrow(senderEntity, chatEntity)
        return userChatEntity
    }
}