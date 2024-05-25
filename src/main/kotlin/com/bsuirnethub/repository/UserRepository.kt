package com.bsuirnethub.repository

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUserId(userId: UserId): UserEntity?
    fun deleteByUserId(userId: UserId)
}
