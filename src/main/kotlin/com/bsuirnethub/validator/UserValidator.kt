package com.bsuirnethub.validator

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.rest_status_exception.RestStatusException
import com.bsuirnethub.exception.error_code.rest.UserRestErrorCode
import org.springframework.stereotype.Component

@Component
class UserValidator {
    fun validateUserDeletion(deletedCount: Int, userId: UserId) {
        if (deletedCount == 0)
            throw RestStatusException(UserRestErrorCode.USER_NOT_FOUND, userId)
    }

    fun validateUserDoesNotExists(userId: UserId, operation: () -> UserId?): UserId? {
        return validateEntityDoesNotExists(operation, UserRestErrorCode.USER_ALREADY_EXISTS, userId)
    }

    fun validateUserExist(userEntity: UserEntity?, userId: UserId): UserEntity {
        return userEntity ?: throw RestStatusException(UserRestErrorCode.USER_NOT_FOUND, userId)
    }

    fun validateAllUserEntitiesExistence(userIds: List<UserId>, userEntities: List<UserEntity>) {
        if (userEntities.size != userIds.size) {
            val missingUsers = userIds.filterNot { userId ->
                userEntities.any { user -> user.userId == userId }
            }
            throw RestStatusException(UserRestErrorCode.USERS_NOT_FOUND, missingUsers)
        }
    }
}