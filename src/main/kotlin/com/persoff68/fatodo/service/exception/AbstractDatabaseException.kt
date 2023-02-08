package com.persoff68.fatodo.service.exception

import com.persoff68.fatodo.exception.AbstractException
import org.springframework.http.HttpStatus

abstract class AbstractDatabaseException(
    status: HttpStatus, message: String, feedbackCode: String
) : AbstractException(status, message, feedbackCode)
