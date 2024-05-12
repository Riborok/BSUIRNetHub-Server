package com.bsuirnethub.extension

import com.bsuirnethub.entity.UserEntity

fun Collection<UserEntity>.getUserIds(): List<String> {
    return this.map { it.userId!! }
}