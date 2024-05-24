package com.bsuirnethub.model

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.UserEntity
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class User private constructor(
    var userId: UserId?,
    var subscriptionIds: List<User>?,
    var subscriberIds: List<User>?
) {
    class Builder(private val userEntity: UserEntity) {
        private var userId: UserId? = null
        private var subscriptionIds: List<User>? = null
        private var subscriberIds: List<User>? = null

        fun userId() = apply { this.userId = userEntity.userId }
        fun subscriptionIds() = apply { this.subscriptionIds = userEntity.subscriptions.toUserIds() }
        fun subscriberIds() = apply { this.subscriberIds = userEntity.subscribers.toUserIds() }

        fun build() = User(userId, subscriptionIds, subscriberIds)
    }
}

fun UserEntity.toUserId(): User {
    return User.Builder(this)
        .userId()
        .build()
}

fun UserEntity.toUserInfo(): User {
    return User.Builder(this)
        .userId()
        .subscriptionIds()
        .subscriberIds()
        .build()
}

fun Collection<UserEntity>.toUserIds(): List<User> {
    return this.map { it.toUserId() }
}

fun Collection<UserEntity>.toUserInfos(): List<User> {
    return this.map { it.toUserInfo() }
}