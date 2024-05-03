package com.bsuirnethub.model

import com.bsuirnethub.entity.UserEntity

data class User(
    var userId: String? = null,
    var contacts: List<Contact> = ArrayList()
) {
    companion object {
        fun toModel(entity: UserEntity): User {
            return User(entity.userId, entity.contacts.map(Contact::toModel).toList())
        }
    }
}
