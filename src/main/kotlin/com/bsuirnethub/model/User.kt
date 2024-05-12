package com.bsuirnethub.model

import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.extension.getUserIds

class User private constructor(
    var userId: String?,
    var subscriptionIds: List<String>?,
    var subscriberIds: List<String>?
) {
    class Builder(private val userEntity: UserEntity) {
        private var userId: String? = null
        private var subscriptionIds: List<String>? = null
        private var subscriberIds: List<String>? = null

        fun userId() = apply { this.userId = userEntity.userId }
        fun subscriptionIds() = apply { this.subscriptionIds = userEntity.subscriptions.getUserIds() }
        fun subscriberIds() = apply { this.subscriberIds = userEntity.subscribers.getUserIds() }

        fun build() = User(userId, subscriptionIds, subscriberIds)
    }
}
