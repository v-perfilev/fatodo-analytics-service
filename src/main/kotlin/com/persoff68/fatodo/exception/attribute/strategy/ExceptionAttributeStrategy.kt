package com.persoff68.fatodo.exception.attribute.strategy

import com.persoff68.fatodo.exception.attribute.util.AttributeUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest

class ExceptionAttributeStrategy(
    private val request: HttpServletRequest,
    exception: Exception
) : AbstractAttributeStrategy(exception) {

    companion object {
        const val REQUEST_URI_PATH = "javax.servlet.error.request_uri"
    }

    override fun addPath() {
        val webRequest: WebRequest = ServletWebRequest(request)
        var path = AttributeUtils.getAttribute<String>(webRequest, REQUEST_URI_PATH)
        path = path ?: request.requestURI
        path?.let { errorAttributes["path"] = path }
    }

}
