package com.persoff68.fatodo.exception

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class CommonExceptionTest {

    @Test
    fun testClientException() {
        val exception = ClientException()
        Assertions.assertThat(exception).isInstanceOf(AbstractException::class.java)
        val abstractException = exception as AbstractException
        Assertions.assertThat(abstractException.status).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
    }

}
