package com.bsuirnethub.component.validator

import com.bsuirnethub.alias.GroupId
import com.bsuirnethub.alias.UserId
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.exception.error_code.GroupErrorCode
import org.springframework.stereotype.Component

@Component
class GroupValidator {
    fun validateGroupDoesNotExists(userId: UserId, groupId: GroupId, operation: () -> GroupId?): GroupId? {
        return validateEntityDoesNotExists(operation, GroupErrorCode.GROUP_ALREADY_EXISTS, groupId)
    }

    fun validateGroupDeletion(deletedCount: Int, userId: UserId, groupId: GroupId) {
        if (deletedCount == 0)
            throw RestStatusException(GroupErrorCode.GROUP_NOT_FOUND, groupId)
    }
}
