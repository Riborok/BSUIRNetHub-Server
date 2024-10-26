package com.bsuirnethub.service

import com.bsuirnethub.alias.GroupId
import com.bsuirnethub.alias.UserId
import com.bsuirnethub.finder.UserFinder
import com.bsuirnethub.validator.GroupValidator
import com.bsuirnethub.entity.GroupEntity
import com.bsuirnethub.repository.GroupRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GroupService(
    private val userFinder: UserFinder,
    private val groupRepository: GroupRepository,
    private val groupValidator: GroupValidator
) {
    fun addGroup(userId: UserId, groupId: GroupId): GroupId? {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        val groupEntity = GroupEntity(groupId = groupId, user = userEntity)
        return groupValidator.validateGroupDoesNotExists(userId, groupId) {
            val savedGroupEntity = groupRepository.save(groupEntity)
            savedGroupEntity.groupId
        }
    }

    fun deleteGroup(userId: UserId, groupId: GroupId) {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        val deletedCount = groupRepository.deleteByGroupIdAndUser(groupId, userEntity)
        groupValidator.validateGroupDeletion(deletedCount, userId, groupId)
    }

    fun getGroupIds(userId: UserId): List<GroupId?> {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        val groupEntities = userEntity.groups
        return groupEntities.map { it.groupId }
    }
}
