package com.bsuirnethub.model

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.extension.getUserIds
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class User private constructor(
    var userId: UserId?,
    var subscriptionIds: List<UserId>?,
    var subscriberIds: List<UserId>?
) {
    class Builder(private val userEntity: UserEntity) {
        private var userId: UserId? = null
        private var subscriptionIds: List<UserId>? = null
        private var subscriberIds: List<UserId>? = null

        fun userId() = apply { this.userId = userEntity.userId }
        fun subscriptionIds() = apply { this.subscriptionIds = userEntity.subscriptions.getUserIds() }
        fun subscriberIds() = apply { this.subscriberIds = userEntity.subscribers.getUserIds() }

        fun build() = User(userId, subscriptionIds, subscriberIds)
    }
}
