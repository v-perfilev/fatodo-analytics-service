package com.persoff68.fatodo.security.exception

import com.persoff68.fatodo.exception.AbstractException
import org.springframework.http.HttpStatus

class ForbiddenException : AbstractException(HttpStatus.FORBIDDEN, "Access forbidden", "security.forbidden")
