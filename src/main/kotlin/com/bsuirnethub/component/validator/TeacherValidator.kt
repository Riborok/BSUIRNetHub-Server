package com.bsuirnethub.component.validator

import com.bsuirnethub.alias.TeacherId
import com.bsuirnethub.alias.UserId
import com.bsuirnethub.exception.RestStatusException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class TeacherValidator {
    fun validateTeacherDoesNotExists(userId: UserId, teacherId: TeacherId, operation: () -> TeacherId?): TeacherId? {
        return validateEntityDoesNotExists(operation, "Teacher already exists for userId $userId and teacherId $teacherId")
    }

    fun validateTeacherDeletion(deletedCount: Int, userId: UserId, teacherId: TeacherId) {
        if (deletedCount == 0)
            throw RestStatusException("Teacher with id $teacherId not found for user with id $userId", HttpStatus.NOT_FOUND)
    }
}