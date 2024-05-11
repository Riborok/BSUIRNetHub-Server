package com.bsuirnethub.exception

import org.springframework.http.HttpStatus

class RestStatusException(message: String, val status: HttpStatus) : RuntimeException(message)
