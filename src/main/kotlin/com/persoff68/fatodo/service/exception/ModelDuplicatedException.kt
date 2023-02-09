package com.persoff68.fatodo.service.exception

import org.springframework.http.HttpStatus

class ModelDuplicatedException : AbstractDatabaseException {
    companion object {
        private const val MESSAGE = "Model duplicated in database"
        private const val MESSAGE_WITH_PARAM = " duplicated in database"
        private const val FEEDBACK_CODE = "model.duplicated"
    }

    constructor() : super(HttpStatus.CONFLICT, MESSAGE, FEEDBACK_CODE)

    constructor(clazz: Class<Any>) : super(HttpStatus.CONFLICT, clazz.simpleName + MESSAGE_WITH_PARAM, FEEDBACK_CODE)
}
