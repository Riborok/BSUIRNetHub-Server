package com.bsuirnethub.model

import com.bsuirnethub.entity.MessageEntity
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
class Message(
    var chat: Chat?,
    var sender: User?,
    var content: String?,
    var timestamp: LocalDateTime,
)

fun MessageEntity.toModel(): Message {
    return Message(chat?.toChatId(), sender?.toUserId(), content, timestamp)
}

fun Collection<MessageEntity>.toModels(): List<Message> {
    return this.map { it.toModel() }
}