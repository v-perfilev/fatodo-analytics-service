package com.persoff68.fatodo.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.persoff68.fatodo.exception.AbstractException
import com.persoff68.fatodo.exception.attribute.AttributeHandler
import com.persoff68.fatodo.security.exception.ForbiddenException
import com.persoff68.fatodo.security.exception.UnauthorizedException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class SecurityProblemSupport(private val objectMapper: ObjectMapper) : AuthenticationEntryPoint, AccessDeniedHandler {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authenticationException: AuthenticationException
    ) {
        val cause = authenticationException.cause
        val exception = if (authenticationException is InternalAuthenticationServiceException &&
            cause is AbstractException
        ) cause else UnauthorizedException()
        AttributeHandler(request, exception).sendError(objectMapper, response)
    }

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        val exception = ForbiddenException()
        AttributeHandler(request, exception).sendError(objectMapper, response)
    }
}
