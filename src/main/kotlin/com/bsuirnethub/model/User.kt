package com.bsuirnethub.model

import java.time.LocalDateTime

class User(
    var userId: String? = null,
    var lastSeen: LocalDateTime? = null,
    var subscribers: List<Subscriber>? = null
) {
    class Builder {
        private var userId: String? = null
        private var lastSeen: LocalDateTime? = null
        private var subscribers: List<Subscriber>? = null

        fun userId(userId: String?) = apply { this.userId = userId }
        fun lastSeen(lastSeen: LocalDateTime?) = apply { this.lastSeen = lastSeen }
        fun subscribers(subscribers: List<Subscriber>?) = apply { this.subscribers = subscribers }

        fun build() = User(userId, lastSeen, subscribers)
    }
}
