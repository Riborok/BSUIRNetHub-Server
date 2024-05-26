package com.bsuirnethub.service

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.component.finder.UserFinder
import com.bsuirnethub.component.validator.UserValidator
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.model.User
import com.bsuirnethub.model.toModel
import com.bsuirnethub.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userFinder: UserFinder,
    private val userRepository: UserRepository,
    private val userValidator: UserValidator,
) {
    fun createUser(userId: UserId): UserId? {
        var userEntity = UserEntity(userId = userId)
        return userValidator.validateUserDoesNotExists(userId) {
            userEntity = userRepository.save(userEntity)
            userEntity.userId
        }
    }

    fun deleteUser(userId: UserId) {
        val deletedCount = userRepository.deleteByUserId(userId)
        userValidator.validateUserDeletion(deletedCount, userId)
    }

    fun getUserInfo(userId: UserId): User {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        return userEntity.toModel()
    }

    fun getUserIds(): List<UserId?> {
        val userEntities = userRepository.findAll()
        return userEntities.map { it.userId }
    }
}
