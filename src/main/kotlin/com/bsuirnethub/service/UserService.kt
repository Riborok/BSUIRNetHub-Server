package com.bsuirnethub.service

import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(private val userRepository: UserRepository) {
    fun updateLastSeen(userId: String, lastSeen: LocalDateTime): UserEntity {
        val user = findUserOrThrow(userId)
        validateLastSeen(lastSeen, user.lastSeen)
        user.lastSeen = lastSeen
        return userRepository.save(user)
    }

    fun findUserOrThrow(userId: String): UserEntity {
        return userRepository.findByUserId(userId)
            ?: throw RestStatusException("User with id $userId not found", HttpStatus.NOT_FOUND)
    }

    private fun validateLastSeen(lastSeen: LocalDateTime, userLastSeen: LocalDateTime?) {
        if (lastSeen < userLastSeen || lastSeen > LocalDateTime.now()) {
            throw RestStatusException("Invalid lastSeen value", HttpStatus.BAD_REQUEST)
        }
    }
}
