package com.bsuirnethub.repository

import com.bsuirnethub.entity.SubscriptionEntity
import com.bsuirnethub.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscriptionRepository : JpaRepository<SubscriptionEntity, Long> {
    fun deleteByUserAndSubscription(user: UserEntity, subscription: UserEntity): Int
}
