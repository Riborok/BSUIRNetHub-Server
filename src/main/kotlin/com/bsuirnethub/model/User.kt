package com.bsuirnethub.model

import com.bsuirnethub.entity.UserEntity
import java.time.LocalDateTime

class User(
    var userId: String? = null,
    var lastSeen: LocalDateTime? = null,
    var contacts: List<Contact> = ArrayList()
) {
    companion object {
        fun toModel(entity: UserEntity): User {
            return User(entity.userId, entity.lastSeen, entity.contacts.map(Contact::toModel).toList())
        }
    }
}
