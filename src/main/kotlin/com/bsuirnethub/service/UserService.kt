package com.bsuirnethub.service

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.component.UserFinder
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.extension.getUserIds
import com.bsuirnethub.model.User
import com.bsuirnethub.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(private val userFinder: UserFinder, private val userRepository: UserRepository) {
    fun createUser(userId: UserId) {
        val user = UserEntity(userId = userId)
        userRepository.save(user)
    }

    fun deleteUser(userId: UserId) {
        userRepository.deleteByUserId(userId)
    }

    fun getUserById(userId: UserId): User {
        val user = userFinder.findUserEntityOrThrow(userId)
        return User.Builder(user)
            .userId()
            .subscriptionIds()
            .subscriberIds()
            .build()
    }

    fun addSubscription(userId: UserId, subscriptionId: UserId) {
        validateUserIsNotSubscription(userId, subscriptionId)
        val user = userFinder.findUserEntityOrThrow(userId)
        val subscription = userFinder.findUserEntityOrThrow(subscriptionId)
        user.subscriptions.add(subscription)
        userRepository.save(user)
    }

    private fun validateUserIsNotSubscription(userId: UserId, subscriptionId: UserId) {
        if (userId == subscriptionId) {
            throw RestStatusException("You can't subscribe to yourself", HttpStatus.BAD_REQUEST)
        }
    }

    fun deleteSubscription(userId: UserId, subscriptionId: UserId) {
        val user = userFinder.findUserEntityOrThrow(userId)
        val subscription = userFinder.findUserEntityOrThrow(subscriptionId)
        user.subscriptions.remove(subscription)
        userRepository.save(user)
    }

    fun getSubscriptionIds(userId: UserId): List<UserId> {
        val user = userFinder.findUserEntityOrThrow(userId)
        return user.subscriptions.getUserIds()
    }

    fun getSubscriberIds(userId: UserId): List<UserId> {
        val user = userFinder.findUserEntityOrThrow(userId)
        return user.subscribers.getUserIds()
    }
}
