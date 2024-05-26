package com.bsuirnethub.model

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.ChatEntity

class Chat(
    var id: Long?,
    var participantIds: List<UserId?>?
)

fun ChatEntity.toModel(): Chat {
    return Chat(id, participants.map { it.userId })
}

fun Collection<ChatEntity>.toModels(): List<Chat> {
    return this.map { it.toModel() }
}
