package com.persoff68.fatodo.exception

import com.persoff68.fatodo.security.exception.ForbiddenException
import com.persoff68.fatodo.security.exception.UnauthorizedException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class SecurityExceptionTest {
    @Test
    fun testForbiddenException() {
        val exception = ForbiddenException()
        Assertions.assertThat(exception).isInstanceOf(AbstractException::class.java)
        val abstractException = exception as AbstractException
        Assertions.assertThat(abstractException.status).isEqualTo(HttpStatus.FORBIDDEN)
        Assertions.assertThat(abstractException.feedbackCode).isEqualTo("security.forbidden")
    }

    @Test
    fun testUnauthorizedException() {
        val exception = UnauthorizedException()
        Assertions.assertThat(exception).isInstanceOf(AbstractException::class.java)
        val abstractException = exception as AbstractException
        Assertions.assertThat(abstractException.status).isEqualTo(HttpStatus.UNAUTHORIZED)
        Assertions.assertThat(abstractException.feedbackCode).isEqualTo("security.unauthorized")
    }
}
