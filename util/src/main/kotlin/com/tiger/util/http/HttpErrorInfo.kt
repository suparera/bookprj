package com.tiger.util.http

import org.springframework.http.HttpStatus
import java.time.ZonedDateTime

class HttpErrorInfo(
    val timestamp: ZonedDateTime? = null,
    val path: String? = null,
    val httpStatus: HttpStatus? = null,
    val message: String? = null,
) {
    constructor(httpStatus: HttpStatus, path: String, message: String) : this(
        timestamp = ZonedDateTime.now(),
        path = path,
        httpStatus = httpStatus,
        message = message
    )

    fun getStatus(): Int? = httpStatus?.value()

    fun getError(): String? = httpStatus?.reasonPhrase
}