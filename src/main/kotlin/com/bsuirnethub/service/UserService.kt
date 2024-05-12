package com.bsuirnethub.service

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
class UserService(private val userRepository: UserRepository) {
    fun createUser(userId: String) {
        val user = UserEntity(userId = userId)
        userRepository.save(user)
    }

    fun deleteUser(userId: String) {
        userRepository.deleteByUserId(userId)
    }

    fun getUserById(userId: String): User {
        val user = findUserEntityOrThrow(userId)
        return User.Builder(user)
            .userId()
            .subscriptionIds()
            .subscriberIds()
            .build()
    }

    private fun findUserEntityOrThrow(userId: String): UserEntity {
        return userRepository.findByUserId(userId)
            ?: throw RestStatusException("User with id $userId not found", HttpStatus.NOT_FOUND)
    }

    fun addSubscription(userId: String, subscriptionId: String) {
        validateUserIsNotSubscription(userId, subscriptionId)
        val user = findUserEntityOrThrow(userId)
        val subscription = findUserEntityOrThrow(subscriptionId)
        user.subscriptions.add(subscription)
        userRepository.save(user)
    }

    private fun validateUserIsNotSubscription(userId: String, subscriptionId: String) {
        if (userId == subscriptionId) {
            throw RestStatusException("You can't subscribe to yourself", HttpStatus.BAD_REQUEST)
        }
    }

    fun deleteSubscription(userId: String, subscriptionId: String) {
        val user = findUserEntityOrThrow(userId)
        val subscription = findUserEntityOrThrow(subscriptionId)
        user.subscriptions.remove(subscription)
        userRepository.save(user)
    }

    fun getSubscriptionIds(userId: String): List<String> {
        val user = findUserEntityOrThrow(userId)
        return user.subscriptions.getUserIds()
    }

    fun getSubscriberIds(userId: String): List<String> {
        val user = findUserEntityOrThrow(userId)
        return user.subscribers.getUserIds()
    }
}
