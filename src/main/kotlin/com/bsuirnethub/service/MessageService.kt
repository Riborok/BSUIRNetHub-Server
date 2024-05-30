package com.bsuirnethub.service

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.component.finder.ChatFinder
import com.bsuirnethub.component.finder.UserChatFinder
import com.bsuirnethub.component.finder.UserFinder
import com.bsuirnethub.component.validator.ChatValidator
import com.bsuirnethub.component.validator.MessageValidator
import com.bsuirnethub.entity.MessageEntity
import com.bsuirnethub.model.Message
import com.bsuirnethub.model.toModel
import com.bsuirnethub.model.toModels
import com.bsuirnethub.repository.MessageRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MessageService(
    private val userFinder: UserFinder,
    private val chatFinder: ChatFinder,
    private val messageRepository: MessageRepository,
    private val userChatFinder: UserChatFinder,
    private val chatValidator: ChatValidator,
    private val messageValidator: MessageValidator
) {
    fun saveMessage(senderId: UserId, chatId: Long, content: String): Message {
        val chatEntity = chatFinder.findChatEntityByIdOrThrow(chatId)
        chatValidator.validateSenderIdInParticipants(senderId, chatEntity)
        val senderEntity = userFinder.findUserEntityByIdOrThrow(senderId)
        val messageEntity = MessageEntity(chat = chatEntity, sender = senderEntity, content = content)
        val savedMessageEntity = messageRepository.save(messageEntity)
        return savedMessageEntity.toModel()
    }

    fun getUnreadMessages(senderId: UserId, chatId: Long): List<Message> {
        val senderEntity = userFinder.findUserEntityByIdOrThrow(senderId)
        val chatEntity = chatFinder.findChatEntityByIdOrThrow(chatId)
        val userChatEntity = userChatFinder.findUserChatEntityByUserEntityAndChatEntityOrThrow(senderEntity, chatEntity)
        val unreadMessages = userChatEntity.unreadMessages
        val messageEntities = messageRepository.findFirstNByChatOrderByTimestampDesc(chatEntity, unreadMessages)
        return messageEntities.toModels()
    }

    fun getMessagesPage(senderId: UserId, chatId: Long, pageNumber: Int, pageSize: Int): List<Message> {
        val chatEntity = chatFinder.findChatEntityByIdOrThrow(chatId)
        chatValidator.validateSenderIdInParticipants(senderId, chatEntity)
        messageValidator.validatePageRequest(pageNumber, pageSize)
        val pageRequest = PageRequest.of(pageNumber, pageSize)
        val messageEntities = messageRepository.findByChatOrderByTimestampDesc(chatEntity, pageRequest)
        return messageEntities.toModels()
    }

    fun getMessages(senderId: UserId, chatId: Long): List<Message> {
        val chatEntity = chatFinder.findChatEntityByIdOrThrow(chatId)
        chatValidator.validateSenderIdInParticipants(senderId, chatEntity)
        val messageEntities = chatEntity.messages
        return messageEntities.toModels()
    }
}