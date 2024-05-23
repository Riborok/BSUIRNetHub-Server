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
        fun subscriptionIds() = apply { this.subscriptionIds = userEntity.subscriptions.toShallowUsers() }
        fun subscriberIds() = apply { this.subscriberIds = userEntity.subscribers.toShallowUsers() }

        fun build() = User(userId, subscriptionIds, subscriberIds)
    }
}

fun UserEntity.toShallowUser(): User {
    return User.Builder(this)
        .userId()
        .build()
}

fun UserEntity.toFullUser(): User {
    return User.Builder(this)
        .userId()
        .subscriptionIds()
        .subscriberIds()
        .build()
}

fun Collection<UserEntity>.toShallowUsers(): List<User> {
    return this.map { it.toShallowUser() }
}

fun Collection<UserEntity>.toFullUsers(): List<User> {
    return this.map { it.toFullUser() }
}