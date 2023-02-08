package com.persoff68.fatodo.exception.attribute

import com.fasterxml.jackson.databind.ObjectMapper
import com.persoff68.fatodo.exception.attribute.strategy.AttributeStrategy
import com.persoff68.fatodo.exception.attribute.strategy.ExceptionAttributeStrategy
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class AttributeHandler(request: HttpServletRequest, exception: Exception) {

    private var attributeStrategy: AttributeStrategy

    init {
        this.attributeStrategy = ExceptionAttributeStrategy(request, exception)
    }

    fun getResponseEntity(objectMapper: ObjectMapper): ResponseEntity<String> {
        val status = getStatus()
        val body = objectMapper.writeValueAsString(getErrorAttributes())
        return ResponseEntity.status(status).body(body)
    }

    fun sendError(objectMapper: ObjectMapper, response: HttpServletResponse) {
        val status = getStatus()
        val body = objectMapper.writeValueAsString(getErrorAttributes())
        response.sendError(status.value(), body)
    }

    private fun getErrorAttributes(): Map<String, Any> {
        attributeStrategy.addTimestamp()
        attributeStrategy.addStatus()
        attributeStrategy.addErrorDetails()
        attributeStrategy.addFeedbackCode()
        attributeStrategy.addPath()
        return attributeStrategy.getErrorAttributes()
    }

    private fun getStatus(): HttpStatus {
        return attributeStrategy.getStatus()
    }

}
