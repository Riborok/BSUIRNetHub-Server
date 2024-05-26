package com.bsuirnethub.service

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.component.UserFinder
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.model.User
import com.bsuirnethub.model.toModel
import com.bsuirnethub.repository.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userFinder: UserFinder,
    private val userRepository: UserRepository
) {
    fun createUser(userId: UserId): UserId? {
        var userEntity = UserEntity(userId = userId)
        return try {
            userEntity = userRepository.save(userEntity)
            userEntity.userId
        } catch (e: DataIntegrityViolationException) {
            throw RestStatusException("User with id $userId already exists", HttpStatus.CONFLICT)
        }
    }

    fun deleteUser(userId: UserId) {
        val deletedCount = userRepository.deleteByUserId(userId)
        validateUserDeletion(deletedCount, userId)
    }

    private fun validateUserDeletion(deletedCount: Int, userId: UserId) {
        if (deletedCount == 0)
            throw RestStatusException("User with id $userId not found", HttpStatus.NOT_FOUND)
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
