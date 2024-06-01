package com.bsuirnethub.service

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.finder.UserFinder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SubscriberService(
    private val userFinder: UserFinder,
) {
    fun getSubscriberIds(userId: UserId): List<UserId?> {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        val subscriberEntities = userEntity.subscribers
        return subscriberEntities.map { it.user?.userId }
    }
}