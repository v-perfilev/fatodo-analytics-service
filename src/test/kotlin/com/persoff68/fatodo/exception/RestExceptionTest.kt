package com.persoff68.fatodo.exception

import com.persoff68.fatodo.web.rest.exception.InvalidFormException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class RestExceptionTest {
    @Test
    fun testInvalidFormException() {
        val exception = InvalidFormException()
        Assertions.assertThat(exception).isInstanceOf(AbstractException::class.java)
        val abstractException = exception as AbstractException
        Assertions.assertThat(abstractException.status).isEqualTo(HttpStatus.BAD_REQUEST)
        Assertions.assertThat(abstractException.feedbackCode).isEqualTo("form.invalid")
    }
}
