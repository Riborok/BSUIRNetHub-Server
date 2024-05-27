package com.bsuirnethub.component.validator

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.RestStatusException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class UserValidator {
    fun validateUserDeletion(deletedCount: Int, userId: UserId) {
        if (deletedCount == 0)
            throw RestStatusException("User with id $userId not found", HttpStatus.NOT_FOUND)
    }

    fun validateUserDoesNotExists(userId: UserId, operation: () -> UserId?): UserId? {
        return validateEntityDoesNotExists(operation, "User with id $userId already exists")
    }

    fun validateUserExist(chatEntity: UserEntity?, userId: UserId): UserEntity {
        return chatEntity ?: throw RestStatusException("User with id $userId not found", HttpStatus.NOT_FOUND)
    }

    fun validateAllUserEntitiesExistence(userIds: List<UserId>, users: List<UserEntity>) {
        if (users.size != userIds.size) {
            val missingUsers = userIds.filterNot { userId ->
                users.any { user -> user.userId == userId }
            }
            throw RestStatusException("Users with ids $missingUsers not found", HttpStatus.NOT_FOUND)
        }
    }
}