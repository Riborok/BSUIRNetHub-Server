package com.bsuirnethub.model

import com.bsuirnethub.entity.ChatEntity
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class Chat private constructor(
    var id: Long?,
    var participantIds: List<User>?
) {
    class Builder(private val chatEntity: ChatEntity) {
        private var id: Long? = null
        private var participantIds: List<User>? = null

        fun id() = apply { this.id = chatEntity.id }
        fun participantIds() = apply { this.participantIds = chatEntity.participants.toUserIds() }

        fun build() = Chat(id, participantIds)
    }
}

fun ChatEntity.toChatId(): Chat {
    return Chat.Builder(this)
        .id()
        .build()
}

fun ChatEntity.toChatInfo(): Chat {
    return Chat.Builder(this)
        .id()
        .participantIds()
        .build()
}

fun Collection<ChatEntity>.toChatIds(): List<Chat> {
    return this.map { it.toChatId() }
}

fun Collection<ChatEntity>.toChatInfos(): List<Chat> {
    return this.map { it.toChatInfo() }
}
