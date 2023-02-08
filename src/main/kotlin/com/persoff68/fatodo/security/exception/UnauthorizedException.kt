package com.persoff68.fatodo.security.exception

import com.persoff68.fatodo.exception.AbstractException
import org.springframework.http.HttpStatus

class UnauthorizedException : AbstractException(
    HttpStatus.FORBIDDEN,
    "Authentication required",
    "security.unauthorized"
)
