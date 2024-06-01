package com.bsuirnethub.exception.error_code.rest

import com.bsuirnethub.exception.error_code.ErrorCode
import org.springframework.http.HttpStatus

interface RestErrorCode: ErrorCode {
    val status: HttpStatus
}
