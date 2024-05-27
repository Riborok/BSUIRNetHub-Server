package com.bsuirnethub.exception.error_code

import org.springframework.http.HttpStatus

interface ErrorCode {
    val code: Int
    val message: String
    val status: HttpStatus
}
