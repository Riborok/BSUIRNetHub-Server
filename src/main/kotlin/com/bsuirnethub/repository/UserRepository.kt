package com.bsuirnethub.repository

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUserId(userId: UserId): UserEntity?

    @Transactional
    @Modifying
    @Query("DELETE FROM UserEntity u WHERE u.userId = :userId")
    fun deleteByUserId(userId: UserId)
}
