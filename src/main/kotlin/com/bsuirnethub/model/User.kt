package com.bsuirnethub.model

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.UserEntity

// TODO: Public information that is used to display profile
class User (
    var userId: UserId?,
)

fun UserEntity.toModel(): User {
    return User(userId)
}

fun Collection<UserEntity>.toModels(): List<User> {
    return this.map { it.toModel() }
}