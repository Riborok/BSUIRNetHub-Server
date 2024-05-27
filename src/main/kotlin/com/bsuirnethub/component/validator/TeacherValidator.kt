package com.bsuirnethub.component.validator

import com.bsuirnethub.alias.TeacherId
import com.bsuirnethub.alias.UserId
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.exception.error_code.TeacherErrorCode
import org.springframework.stereotype.Component

@Component
class TeacherValidator {
    fun validateTeacherDoesNotExists(userId: UserId, teacherId: TeacherId, operation: () -> TeacherId?): TeacherId? {
        return validateEntityDoesNotExists(operation, TeacherErrorCode.TEACHER_ALREADY_EXISTS, teacherId)
    }

    fun validateTeacherDeletion(deletedCount: Int, userId: UserId, teacherId: TeacherId) {
        if (deletedCount == 0)
            throw RestStatusException(TeacherErrorCode.TEACHER_NOT_FOUND, teacherId)
    }
}