package com.bsuirnethub.service

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.component.UserFinder
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.extension.toShallowUsers
import com.bsuirnethub.model.User
import com.bsuirnethub.repository.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(private val userFinder: UserFinder, private val userRepository: UserRepository) {
    fun createUser(userId: UserId): User {
        try {
            var userEntity = UserEntity(userId = userId)
            userEntity = userRepository.save(userEntity)
            return User.Builder(userEntity).buildShallow()
        } catch (e: DataIntegrityViolationException) {
            throw RestStatusException("User with id $userId already exists", HttpStatus.CONFLICT)
        }
    }

    fun deleteUser(userId: UserId) {
        userRepository.deleteByUserId(userId)
    }

    fun getUserById(userId: UserId): User {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        return User.Builder(userEntity).buildFull()
    }

    fun addSubscription(userId: UserId, subscriptionId: UserId): User {
        validateUserIsNotSubscription(userId, subscriptionId)
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        val subscriptionEntity = userFinder.findUserEntityByIdOrThrow(subscriptionId)
        userEntity.subscriptions.add(subscriptionEntity)
        userRepository.save(userEntity)
        return User.Builder(subscriptionEntity).buildShallow()
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
        return userEntity.subscriptions.toShallowUsers()
    }

    fun getSubscriberIds(userId: UserId): List<User> {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        return userEntity.subscribers.toShallowUsers()
    }
}
