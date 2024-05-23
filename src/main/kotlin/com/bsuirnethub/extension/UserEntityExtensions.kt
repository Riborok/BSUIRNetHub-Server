package com.bsuirnethub.extension

import com.bsuirnethub.model.User
import com.bsuirnethub.entity.UserEntity

fun Collection<UserEntity>.toShallowUsers(): List<User> {
    return this.map { User.Builder(it).buildShallow() }
}