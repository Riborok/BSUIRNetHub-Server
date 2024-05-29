package com.bsuirnethub.exception

import com.bsuirnethub.exception.error_code.ErrorCode

class RestStatusException(
    val errorCode: ErrorCode,
    val source: Any?
) : RuntimeException(errorCode.message)
