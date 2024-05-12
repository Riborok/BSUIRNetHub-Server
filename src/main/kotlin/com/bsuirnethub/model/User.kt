package com.bsuirnethub.model

import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.extension.getUserIds

class User private constructor(
    var userId: String?,
    var subscriptionsId: List<String>?,
    var subscribersId: List<String>?
) {
    class Builder(private val userEntity: UserEntity) {
        private var userId: String? = null
        private var subscriptionsId: List<String>? = null
        private var subscribersId: List<String>? = null

        fun userId() = apply { this.userId = userEntity.userId }
        fun subscriptionsId() = apply { this.subscriptionsId = userEntity.subscriptions.getUserIds() }
        fun subscribersId() = apply { this.subscribersId = userEntity.subscribers.getUserIds() }

        fun build() = User(userId, subscriptionsId, subscribersId)
    }
}
