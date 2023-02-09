package com.persoff68.fatodo.exception.handler

import com.persoff68.fatodo.exception.attribute.util.AttributeUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.ServletWebRequest

@RestController
class ExceptionController : ErrorController {
    @GetMapping("/error")
    fun error(request: HttpServletRequest): ResponseEntity<String> {
        val status = AttributeUtils.getStatus(request)
        val body = AttributeUtils.getMessage(ServletWebRequest(request))
        return ResponseEntity.status(status).body(body)
    }
}
