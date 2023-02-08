package com.persoff68.fatodo.service.exception

import com.persoff68.fatodo.exception.AbstractException
import org.springframework.http.HttpStatus

class PermissionException : AbstractException {
    companion object {
        private const val MESSAGE = "Permission restricted"
        private const val FEEDBACK_CODE = "permission.restricted"
    }

    constructor() : super(HttpStatus.FORBIDDEN, MESSAGE, FEEDBACK_CODE)

    constructor(message: String) : super(HttpStatus.FORBIDDEN, message, FEEDBACK_CODE)

}
