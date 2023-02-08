package com.persoff68.fatodo.exception.attribute.util

import com.persoff68.fatodo.exception.AbstractException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest

object AttributeUtils {
    private const val EXCEPTION_PATH = "javax.servlet.error.exception"
    private const val STATUS_CODE_PATH = "javax.servlet.error.status_code"
    private const val MESSAGE_PATH = "javax.servlet.error.message"

    fun getMessage(webRequest: WebRequest): String? {
        return getAttribute(webRequest, MESSAGE_PATH)
    }

    fun getStatus(request: HttpServletRequest): HttpStatus {
        var status = HttpStatus.INTERNAL_SERVER_ERROR
        val error = getError(ServletWebRequest(request))
        if (error is AbstractException) {
            status = error.status
        } else {
            try {
                val statusCode = request.getAttribute(STATUS_CODE_PATH) as Int?
                statusCode?.let { status = HttpStatus.valueOf(it) }
            } catch (e: Exception) {
                // skip
            }
        }
        return status
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getAttribute(requestAttributes: RequestAttributes, name: String): T? {
        return requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST) as T?
    }

    private fun getError(webRequest: WebRequest): Throwable? {
        return getAttribute(webRequest, EXCEPTION_PATH)
    }

}
