package com.bsuirnethub.extension

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.UserEntity

fun Collection<UserEntity>.getUserIds(): List<UserId> {
    return this.map { it.userId!! }
}