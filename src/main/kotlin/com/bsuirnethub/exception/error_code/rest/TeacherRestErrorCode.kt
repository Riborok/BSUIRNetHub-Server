package com.bsuirnethub.exception.error_code.rest

import com.bsuirnethub.exception.error_code.ErrorCategories
import com.bsuirnethub.exception.error_code.ModelCodes
import org.springframework.http.HttpStatus

enum class TeacherRestErrorCode(errorCategory: Int, override val message: String, override val status: HttpStatus) :
    RestErrorCode {
    TEACHER_NOT_FOUND(ErrorCategories.NOT_FOUND, "Teacher not found", HttpStatus.NOT_FOUND),
    TEACHER_ALREADY_EXISTS(ErrorCategories.ALREADY_EXISTS, "Teacher already exists", HttpStatus.CONFLICT);

    override val code: Int = ModelCodes.TEACHER + errorCategory
}
