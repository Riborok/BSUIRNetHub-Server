package com.bsuirnethub.finder

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.validator.UserValidator
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserFinder(
    private val userRepository: UserRepository,
    private val userValidator: UserValidator
) {
    fun findUserEntityByIdOrThrow(userId: UserId): UserEntity {
        return userValidator.validateUserExist(userRepository.findByUserId(userId), userId)
    }

    fun findUserEntitiesByIdsOrThrow(userIds: List<UserId>): List<UserEntity> {
        val userEntities = findExistingUserEntitiesByIds(userIds)
        userValidator.validateAllUserEntitiesExistence(userIds, userEntities)
        return userEntities
    }

    private fun findExistingUserEntitiesByIds(userIds: List<UserId>): List<UserEntity> {
        return userIds.mapNotNull { userId -> userRepository.findByUserId(userId) }
    }
}
