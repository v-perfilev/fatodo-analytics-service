package com.persoff68.fatodo.service.exception

import com.persoff68.fatodo.exception.AbstractException
import org.springframework.http.HttpStatus

class DatabaseException : AbstractException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error", "model.databaseError")
