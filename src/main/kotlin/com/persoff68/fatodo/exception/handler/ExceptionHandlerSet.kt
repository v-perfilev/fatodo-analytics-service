package com.persoff68.fatodo.exception.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.persoff68.fatodo.exception.AbstractException
import com.persoff68.fatodo.exception.attribute.AttributeHandler
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.io.IOException

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
class ExceptionHandlerSet(private val objectMapper: ObjectMapper) {

    @ExceptionHandler(AbstractException::class)
    @Throws(IOException::class)
    fun handleAbstractException(request: HttpServletRequest, e: AbstractException): ResponseEntity<String> {
        return AttributeHandler(request, e).getResponseEntity(objectMapper)
    }

    @ExceptionHandler(RuntimeException::class)
    @Throws(IOException::class)
    fun handleRuntimeException(request: HttpServletRequest, e: RuntimeException): ResponseEntity<String> {
        val cause = e.cause
        val exception = if (cause is Exception) cause else e
        return AttributeHandler(request, exception).getResponseEntity(objectMapper)
    }

    @ExceptionHandler(Exception::class)
    @Throws(IOException::class)
    fun handleException(request: HttpServletRequest, e: Exception): ResponseEntity<String> {
        return AttributeHandler(request, e).getResponseEntity(objectMapper)
    }

}
