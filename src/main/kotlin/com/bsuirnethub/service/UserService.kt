package com.bsuirnethub.service

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.component.UserFinder
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.model.User
import com.bsuirnethub.model.toUserInfo
import com.bsuirnethub.model.toUserId
import com.bsuirnethub.model.toUserIds
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
    fun createUser(userId: UserId): User {
        try {
            var userEntity = UserEntity(userId = userId)
            userEntity = userRepository.save(userEntity)
            return userEntity.toUserId()
        } catch (e: DataIntegrityViolationException) {
            throw RestStatusException("User with id $userId already exists", HttpStatus.CONFLICT)
        }
    }

    fun deleteUser(userId: UserId) {
        userRepository.deleteByUserId(userId)
    }

    fun getUserInfo(userId: UserId): User {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        return userEntity.toUserInfo()
    }

    fun getUserIds(): List<User> {
        val userEntities = userRepository.findAll()
        return userEntities.toUserIds()
    }
}
