package com.bsuirnethub.service

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.component.finder.UserFinder
import com.bsuirnethub.component.validator.SubscriptionValidator
import com.bsuirnethub.entity.SubscriptionEntity
import com.bsuirnethub.repository.SubscriptionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SubscriptionService(
    private val userFinder: UserFinder,
    private val subscriptionRepository: SubscriptionRepository,
    private val subscriptionValidator: SubscriptionValidator
) {
    fun addSubscription(userId: UserId, subscriptionId: UserId): UserId? {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        subscriptionValidator.validateUserIsNotSubscription(userId, subscriptionId)
        val subscriptionUserEntity = userFinder.findUserEntityByIdOrThrow(subscriptionId)
        return subscriptionValidator.validateSubscriptionDoesNotExists(userId, subscriptionId) {
            val subscriptionEntity = SubscriptionEntity(user = userEntity, subscription = subscriptionUserEntity)
            subscriptionRepository.save(subscriptionEntity)
            subscriptionUserEntity.userId
        }
    }

    fun deleteSubscription(userId: UserId, subscriptionId: UserId) {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        val subscriptionEntity = userFinder.findUserEntityByIdOrThrow(subscriptionId)
        val deletedCount = subscriptionRepository.deleteByUserAndSubscription(userEntity, subscriptionEntity)
        subscriptionValidator.validateSubscriptionDeletion(deletedCount, userId, subscriptionId)
    }

    fun getSubscriptionIds(userId: UserId): List<UserId?> {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        return userEntity.subscriptions.map { it.subscription?.userId }
    }
}