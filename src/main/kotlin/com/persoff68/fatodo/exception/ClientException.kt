package com.persoff68.fatodo.exception

import org.springframework.http.HttpStatus

class ClientException : AbstractException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal client error")
