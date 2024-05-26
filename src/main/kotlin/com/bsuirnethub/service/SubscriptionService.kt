package com.bsuirnethub.service

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.component.UserFinder
import com.bsuirnethub.entity.SubscriptionEntity
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.repository.SubscriptionRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SubscriptionService(
    private val userFinder: UserFinder,
    private val subscriptionRepository: SubscriptionRepository,
) {
    fun addSubscription(userId: UserId, subscriptionId: UserId): UserId? {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        validateUserIsNotSubscription(userId, subscriptionId)
        val subscriptionUserEntity = userFinder.findUserEntityByIdOrThrow(subscriptionId)
        return try {
            val subscriptionEntity = SubscriptionEntity(user = userEntity, subscription = subscriptionUserEntity)
            subscriptionRepository.save(subscriptionEntity)
            subscriptionUserEntity.userId
        } catch (e: DataIntegrityViolationException) {
            throw RestStatusException("Subscription already exists for userId $userId and subscriptionId $subscriptionId", HttpStatus.CONFLICT)
        }
    }

    private fun validateUserIsNotSubscription(userId: UserId, subscriptionId: UserId) {
        if (userId == subscriptionId) {
            throw RestStatusException("You can't subscribe to yourself", HttpStatus.BAD_REQUEST)
        }
    }

    fun deleteSubscription(userId: UserId, subscriptionId: UserId) {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        val subscriptionEntity = userFinder.findUserEntityByIdOrThrow(subscriptionId)
        val deletedCount = subscriptionRepository.deleteByUserAndSubscription(userEntity, subscriptionEntity)
        validateSubscriptionDeletion(deletedCount, userId, subscriptionId)
    }

    private fun validateSubscriptionDeletion(deletedCount: Int, userId: UserId, subscriptionId: UserId) {
        if (deletedCount == 0)
            throw RestStatusException("Subscription with id $subscriptionId not found for user with id $userId", HttpStatus.NOT_FOUND)
    }

    fun getSubscriptionIds(userId: UserId): List<UserId?> {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        return userEntity.subscriptions.map { it.subscription?.userId }
    }

    fun getSubscriberIds(userId: UserId): List<UserId?> {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        return userEntity.subscribers.map { it.user?.userId }
    }
}