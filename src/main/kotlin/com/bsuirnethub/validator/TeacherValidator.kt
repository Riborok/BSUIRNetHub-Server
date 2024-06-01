package com.bsuirnethub.validator

import com.bsuirnethub.alias.TeacherId
import com.bsuirnethub.alias.UserId
import com.bsuirnethub.exception.error_code_exception.RestStatusException
import com.bsuirnethub.exception.error_code.rest.TeacherRestErrorCode
import org.springframework.stereotype.Component

@Component
class TeacherValidator {
    fun validateTeacherDoesNotExists(userId: UserId, teacherId: TeacherId, operation: () -> TeacherId?): TeacherId? {
        return validateEntityDoesNotExists(operation, TeacherRestErrorCode.TEACHER_ALREADY_EXISTS, teacherId)
    }

    fun validateTeacherDeletion(deletedCount: Int, userId: UserId, teacherId: TeacherId) {
        if (deletedCount == 0)
            throw RestStatusException(TeacherRestErrorCode.TEACHER_NOT_FOUND, teacherId)
    }
}