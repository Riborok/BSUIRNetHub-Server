package com.bsuirnethub.exception.error_code

import org.springframework.http.HttpStatus

enum class GroupErrorCode(errorCategory: Int, override val message: String, override val status: HttpStatus) :
    ErrorCode {
    GROUP_NOT_FOUND(ErrorCategories.NOT_FOUND, "Group not found", HttpStatus.NOT_FOUND),
    GROUP_ALREADY_EXISTS(ErrorCategories.ALREADY_EXISTS, "Group already exists", HttpStatus.CONFLICT);

    override val code: Int = ModelCodes.GROUP + errorCategory
}
