package com.tiger.util.http

import com.tiger.api.exceptions.BadRequestException
import com.tiger.api.exceptions.InvalidInputException
import com.tiger.api.exceptions.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalControllerExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler::class.java)

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(request: ServerHttpRequest, ex: NotFoundException): HttpErrorInfo =
        createHttpErrorInfo(NOT_FOUND, request, ex)

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(request: ServerHttpRequest, ex: BadRequestException): HttpErrorInfo = createHttpErrorInfo(BAD_REQUEST, request, ex)

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException::class)
    fun handleInvalidInputException(request: ServerHttpRequest, ex: InvalidInputException): HttpErrorInfo = createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex)

    private fun createHttpErrorInfo(httpStatus: HttpStatus, request: ServerHttpRequest, ex: Exception): HttpErrorInfo {
        val path = request.path.pathWithinApplication().value()
        val message = ex.message ?: ""
        logger.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message)
        return HttpErrorInfo(httpStatus, path, message)
    }
}

