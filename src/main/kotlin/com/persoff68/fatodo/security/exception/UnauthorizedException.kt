package com.persoff68.fatodo.security.exception

import com.persoff68.fatodo.exception.AbstractException
import org.springframework.http.HttpStatus

class UnauthorizedException : AbstractException(
    HttpStatus.UNAUTHORIZED,
    "Authentication required",
    "security.unauthorized"
)
