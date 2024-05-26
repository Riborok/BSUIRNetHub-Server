package com.bsuirnethub.model

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.MessageEntity
import java.time.LocalDateTime

class Message(
    var chatId: Long?,
    var senderId: UserId?,
    var content: String?,
    var timestamp: LocalDateTime,
)

fun MessageEntity.toModel(): Message {
    return Message(chat?.id, sender?.userId, content, timestamp)
}

fun Collection<MessageEntity>.toModels(): List<Message> {
    return this.map { it.toModel() }
}