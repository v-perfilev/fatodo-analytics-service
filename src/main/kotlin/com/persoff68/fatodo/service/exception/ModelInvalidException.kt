package com.persoff68.fatodo.service.exception

import org.springframework.http.HttpStatus

class ModelInvalidException : AbstractDatabaseException {
    companion object {
        private const val MESSAGE = "Model not valid"
        private const val MESSAGE_WITH_PARAM = " not valid"
        private const val FEEDBACK_CODE = "model.invalid"
    }

    constructor() : super(HttpStatus.BAD_REQUEST, MESSAGE, FEEDBACK_CODE)

    constructor(clazz: Class<Any>) : super(HttpStatus.BAD_REQUEST, clazz.simpleName + MESSAGE_WITH_PARAM, FEEDBACK_CODE)
}
