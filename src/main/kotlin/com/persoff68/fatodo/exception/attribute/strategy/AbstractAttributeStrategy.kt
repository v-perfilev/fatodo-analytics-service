package com.persoff68.fatodo.exception.attribute.strategy

import com.persoff68.fatodo.exception.AbstractException
import org.springframework.http.HttpStatus
import java.util.Date

abstract class AbstractAttributeStrategy(private val exception: Exception) : AttributeStrategy {
    val errorAttributes = LinkedHashMap<String, Any>()

    override fun getErrorAttributes(): Map<String, Any> {
        return errorAttributes
    }

    override fun getStatus(): HttpStatus {
        return if (exception is AbstractException) exception.status else HttpStatus.INTERNAL_SERVER_ERROR
    }

    override fun getFeedbackCode(): String? {
        return if (exception is AbstractException) exception.feedbackCode else null
    }

    override fun addTimestamp() {
        errorAttributes["timestamp"] = Date()
    }

    override fun addStatus() {
        val status = getStatus()
        errorAttributes["status"] = status.value()
        errorAttributes["error"] = status.reasonPhrase
    }

    override fun addFeedbackCode() {
        val feedbackCode = getFeedbackCode()
        feedbackCode?.let { errorAttributes["feedbackCode"] = feedbackCode }
    }

    override fun addErrorDetails() {
        val message = exception.message
        message?.let { errorAttributes["message"] = message }
    }
}
