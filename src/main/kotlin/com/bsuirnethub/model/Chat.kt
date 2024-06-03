package com.bsuirnethub.model

import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.repository.MessageRepository
import org.springframework.stereotype.Component

class Chat(
    var id: Long?,
    var userChats: List<UserChat>?,
    var lastMessage: Message?
)

@Component
class ChatConverter(private val messageRepository: MessageRepository) {
    fun toModel(chatEntity: ChatEntity): Chat {
        val lastMessageEntity = messageRepository.findTopByChatOrderByTimestampDesc(chatEntity)
        return Chat(chatEntity.id, chatEntity.userChats.toModels(), lastMessageEntity?.toModel())
    }

    fun toModels(chatEntities: Collection<ChatEntity>): List<Chat> {
        return chatEntities.map { toModel(it) }
    }
}
