package com.persoff68.fatodo.web.rest.exception.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.persoff68.fatodo.exception.attribute.AttributeHandler
import com.persoff68.fatodo.web.rest.exception.InvalidFormException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.io.IOException

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
class RestExceptionHandlerSet(private val objectMapper: ObjectMapper) {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(request: HttpServletRequest): ResponseEntity<String> {
        return handleInvalidFormException(request)
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(request: HttpServletRequest): ResponseEntity<String> {
        return handleInvalidFormException(request)
    }

    @Throws(IOException::class)
    private fun handleInvalidFormException(request: HttpServletRequest): ResponseEntity<String> {
        val exception = InvalidFormException()
        return AttributeHandler(request, exception).getResponseEntity(objectMapper)
    }
}
