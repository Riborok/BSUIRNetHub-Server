package com.bsuirnethub.component

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserInitializer(
    private val userRepository: UserRepository
) {
    final lateinit var userIds: List<UserId>
        private set

    final lateinit var userUserEntities: List<UserEntity>
        private set

    fun createAndSaveUsers(count: Int): UserInitializer {
        userIds = (0 until count).map{ "user$it" }
        userUserEntities = userIds.map { UserEntity(userId = it) }
        userRepository.saveAll(userUserEntities)
        return this
    }
}
