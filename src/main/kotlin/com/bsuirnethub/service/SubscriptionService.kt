package com.bsuirnethub.service

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.component.UserFinder
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.model.User
import com.bsuirnethub.model.toUserId
import com.bsuirnethub.model.toUserIds
import com.bsuirnethub.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SubscriptionService(
    private val userFinder: UserFinder,
    private val userRepository: UserRepository
) {
    fun addSubscription(userId: UserId, subscriptionId: UserId): User {
        validateUserIsNotSubscription(userId, subscriptionId)
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        val subscriptionEntity = userFinder.findUserEntityByIdOrThrow(subscriptionId)
        userEntity.subscriptions.add(subscriptionEntity)
        userRepository.save(userEntity)
        return subscriptionEntity.toUserId()
    }

    private fun validateUserIsNotSubscription(userId: UserId, subscriptionId: UserId) {
        if (userId == subscriptionId) {
            throw RestStatusException("You can't subscribe to yourself", HttpStatus.BAD_REQUEST)
        }
    }

    fun deleteSubscription(userId: UserId, subscriptionId: UserId) {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        val subscriptionEntity = userFinder.findUserEntityByIdOrThrow(subscriptionId)
        userEntity.subscriptions.remove(subscriptionEntity)
        userRepository.save(userEntity)
    }

    fun getSubscriptionIds(userId: UserId): List<User> {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        return userEntity.subscriptions.toUserIds()
    }

    fun getSubscriberIds(userId: UserId): List<User> {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        return userEntity.subscribers.toUserIds()
    }
}