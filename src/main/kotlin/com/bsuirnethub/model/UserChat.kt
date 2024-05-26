package com.bsuirnethub.model

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.UserChatEntity

class UserChat(
    var userId: UserId?,
    var chat: Chat?,
    var unreadMessages: Int
)

fun UserChatEntity.toModel(): UserChat {
    return UserChat(
        user?.userId,
        chat?.toModel(),
        unreadMessages
    )
}

fun Collection<UserChatEntity>.toModels(): List<UserChat> {
    return this.map { it.toModel() }
}