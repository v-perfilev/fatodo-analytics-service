package com.persoff68.fatodo.exception.attribute.strategy

import org.springframework.http.HttpStatus

interface AttributeStrategy {
    fun getErrorAttributes(): Map<String, Any>
    fun getStatus(): HttpStatus
    fun getFeedbackCode(): String?
    fun addTimestamp()
    fun addStatus()
    fun addFeedbackCode()
    fun addErrorDetails()
    fun addPath()
}
