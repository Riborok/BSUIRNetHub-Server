package com.bsuirnethub.component

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class UserFinder(private val userRepository: UserRepository) {
    fun findUserEntityOrThrow(userId: UserId): UserEntity {
        return userRepository.findByUserId(userId)
            ?: throw RestStatusException("User with id $userId not found", HttpStatus.NOT_FOUND)
    }

    fun findUserEntitiesOrThrow(userIds: List<UserId>): List<UserEntity> {
        val users = findExistingUsers(userIds)
        checkUsersExistence(userIds, users)
        return users
    }

    private fun findExistingUsers(userIds: List<UserId>): List<UserEntity> {
        return userIds.mapNotNull { userId ->
            userRepository.findByUserId(userId)
        }
    }

    private fun checkUsersExistence(userIds: List<UserId>, users: List<UserEntity>) {
        if (users.size != userIds.size) {
            val missingUsers = userIds.filterNot { userId ->
                users.any { user -> user.userId == userId }
            }
            throw RestStatusException("Users with ids $missingUsers not found", HttpStatus.NOT_FOUND)
        }
    }
}
