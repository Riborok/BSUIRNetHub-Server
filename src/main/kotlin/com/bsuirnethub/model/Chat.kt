package com.bsuirnethub.model

import com.bsuirnethub.entity.ChatEntity

class Chat(
    var id: Long?,
    var userChats: List<UserChat>?
)

fun ChatEntity.toModel(): Chat {
    return Chat(id, userChats.toModels())
}

fun Collection<ChatEntity>.toModels(): List<Chat> {
    return this.map { it.toModel() }
}
