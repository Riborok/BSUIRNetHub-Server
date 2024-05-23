package com.bsuirnethub.model

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.extension.toShallowUsers
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

        fun buildFull() = apply {
            userId()
            subscriptionIds()
            subscriberIds()
        }.build()

        fun buildShallow() = apply {
            userId()
        }.build()
    }
}
