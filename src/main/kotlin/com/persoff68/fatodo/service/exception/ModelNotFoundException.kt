package com.persoff68.fatodo.service.exception

import org.springframework.http.HttpStatus

class ModelNotFoundException : AbstractDatabaseException {
    companion object {
        private const val MESSAGE = "Model not found in database"
        private const val MESSAGE_WITH_PARAM = " not found in database"
        private const val FEEDBACK_CODE = "model.notFound"
    }

    constructor() : super(HttpStatus.NOT_FOUND, MESSAGE, FEEDBACK_CODE)

    constructor(clazz: Class<Any>) : super(HttpStatus.NOT_FOUND, clazz.simpleName + MESSAGE_WITH_PARAM, FEEDBACK_CODE)
}
