package com.persoff68.fatodo.service.exception

import org.springframework.http.HttpStatus

class ModelAlreadyExistsException : AbstractDatabaseException {
    companion object {
        private const val MESSAGE = "Model already exists in database"
        private const val MESSAGE_WITH_PARAM = " already exists in database"
        private const val FEEDBACK_CODE = "model.exists"
    }

    constructor() : super(HttpStatus.BAD_REQUEST, MESSAGE, FEEDBACK_CODE)

    constructor(clazz: Class<Any>) : super(HttpStatus.BAD_REQUEST, clazz.simpleName + MESSAGE_WITH_PARAM, FEEDBACK_CODE)

}
