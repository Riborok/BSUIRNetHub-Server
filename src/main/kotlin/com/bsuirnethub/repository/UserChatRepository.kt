package com.bsuirnethub.repository

import com.bsuirnethub.entity.UserChatEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserChatRepository : JpaRepository<UserChatEntity, Long>
