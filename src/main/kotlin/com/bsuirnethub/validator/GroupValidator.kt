package com.bsuirnethub.validator

import com.bsuirnethub.alias.GroupId
import com.bsuirnethub.alias.UserId
import com.bsuirnethub.exception.error_code_exception.RestStatusException
import com.bsuirnethub.exception.error_code.rest.GroupRestErrorCode
import org.springframework.stereotype.Component

@Component
class GroupValidator {
    fun validateGroupDoesNotExists(userId: UserId, groupId: GroupId, operation: () -> GroupId?): GroupId? {
        return validateEntityDoesNotExists(operation, GroupRestErrorCode.GROUP_ALREADY_EXISTS, groupId)
    }

    fun validateGroupDeletion(deletedCount: Int, userId: UserId, groupId: GroupId) {
        if (deletedCount == 0)
            throw RestStatusException(GroupRestErrorCode.GROUP_NOT_FOUND, groupId)
    }
}
