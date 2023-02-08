package com.persoff68.fatodo.web.rest.exception

import com.persoff68.fatodo.exception.AbstractException
import org.springframework.http.HttpStatus

class InvalidFormException : AbstractException(HttpStatus.BAD_REQUEST, "Input data is incorrect", "form.invalid")
