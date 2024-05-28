package com.bsuirnethub.exception.error_code

import org.springframework.http.HttpStatus

enum class TeacherErrorCode(errorCategory: Int, override val message: String, override val status: HttpStatus) :
    ErrorCode {
    TEACHER_NOT_FOUND(ErrorCategories.NOT_FOUND, "Teacher not found", HttpStatus.NOT_FOUND),
    TEACHER_ALREADY_EXISTS(ErrorCategories.ALREADY_EXISTS, "Teacher already exists", HttpStatus.CONFLICT);

    override val code: Int = ModelCodes.TEACHER + errorCategory
}