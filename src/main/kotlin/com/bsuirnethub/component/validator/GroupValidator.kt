package com.bsuirnethub.component.validator

import com.bsuirnethub.alias.GroupId
import com.bsuirnethub.alias.UserId
import com.bsuirnethub.exception.RestStatusException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class GroupValidator {
    fun validateGroupDoesNotExists(userId: UserId, groupId: GroupId, operation: () -> GroupId?): GroupId? {
        return validateEntityDoesNotExists(operation, "Group already exists for userId $userId and groupId $groupId")
    }

    fun validateGroupDeletion(deletedCount: Int, userId: UserId, groupId: GroupId) {
        if (deletedCount == 0)
            throw RestStatusException("Group with id $groupId not found for user with id $userId", HttpStatus.NOT_FOUND)
    }
}
