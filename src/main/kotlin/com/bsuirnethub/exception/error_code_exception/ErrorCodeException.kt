package com.bsuirnethub.exception.error_code_exception

import com.bsuirnethub.exception.error_code.ErrorCode

open class ErrorCodeException (
    open val errorCode: ErrorCode,
    val source: Any?
) : RuntimeException(errorCode.message)