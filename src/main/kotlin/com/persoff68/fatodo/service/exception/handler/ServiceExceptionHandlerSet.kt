package com.persoff68.fatodo.service.exception.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.persoff68.fatodo.exception.attribute.AttributeHandler
import com.persoff68.fatodo.service.exception.DatabaseException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.dao.DataAccessException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 20)
class ServiceExceptionHandlerSet(private val objectMapper: ObjectMapper) {

    @ExceptionHandler(DataAccessException::class)
    fun handleDatabaseException(e: Exception, request: HttpServletRequest): ResponseEntity<String> {
        val exception = DatabaseException()
        return AttributeHandler(request, exception).getResponseEntity(objectMapper)
    }

}
