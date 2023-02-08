package com.persoff68.fatodo.exception

import org.springframework.http.HttpStatus

abstract class AbstractException : RuntimeException {

    val status: HttpStatus
    val feedbackCode: String?

    protected constructor(status: HttpStatus, message: String) : super(message) {
        this.status = status
        this.feedbackCode = null
    }

    protected constructor(status: HttpStatus, message: String, feedbackCode: String) : super(message) {
        this.status = status
        this.feedbackCode = feedbackCode
    }

}
