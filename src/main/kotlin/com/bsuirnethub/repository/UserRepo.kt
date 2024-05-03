package com.bsuirnethub.repository

import com.bsuirnethub.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo : JpaRepository<UserEntity, Long> {
    fun findByUserId(userId: String): UserEntity?
}
