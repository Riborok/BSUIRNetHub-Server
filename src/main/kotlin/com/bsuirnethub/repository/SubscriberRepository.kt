package com.bsuirnethub.repository

import com.bsuirnethub.entity.SubscriberEntity
import com.bsuirnethub.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscriberRepository: JpaRepository<SubscriberEntity, Long> {
    fun findByUserAndSubscriberId(user: UserEntity, subscriberId: String): SubscriberEntity?
}
