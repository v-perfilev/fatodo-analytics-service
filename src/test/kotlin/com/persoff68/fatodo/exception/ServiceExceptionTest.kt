package com.persoff68.fatodo.exception

import com.persoff68.fatodo.service.exception.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class ServiceExceptionTest {

    @Test
    fun testDatabaseException() {
        val exception = DatabaseException()
        Assertions.assertThat(exception).isInstanceOf(AbstractException::class.java)
        val abstractException = exception as AbstractException
        Assertions.assertThat(abstractException.status).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
        Assertions.assertThat(abstractException.feedbackCode).isEqualTo("model.databaseError")
    }

    @Test
    fun testModelAlreadyExistsException_firstConstructor() {
        val exception = ModelAlreadyExistsException()
        Assertions.assertThat(exception).isInstanceOf(AbstractException::class.java)
        val abstractException = exception as AbstractException
        Assertions.assertThat(abstractException.status).isEqualTo(HttpStatus.BAD_REQUEST)
        Assertions.assertThat(abstractException.feedbackCode).isEqualTo("model.exists")
    }

    @Test
    fun testModelAlreadyExistsException_secondConstructor() {
        val exception = ModelAlreadyExistsException(Any::class.java)
        Assertions.assertThat(exception).isInstanceOf(AbstractException::class.java)
        val abstractException = exception as AbstractException
        Assertions.assertThat(abstractException.status).isEqualTo(HttpStatus.BAD_REQUEST)
        Assertions.assertThat(abstractException.feedbackCode).isEqualTo("model.exists")
    }

    @Test
    fun testModelDuplicatedException_firstConstructor() {
        val exception = ModelDuplicatedException()
        Assertions.assertThat(exception).isInstanceOf(AbstractException::class.java)
        val abstractException = exception as AbstractException
        Assertions.assertThat(abstractException.status).isEqualTo(HttpStatus.CONFLICT)
        Assertions.assertThat(abstractException.feedbackCode).isEqualTo("model.duplicated")
    }

    @Test
    fun testModelDuplicatedException_secondConstructor() {
        val exception = ModelDuplicatedException(Any::class.java)
        Assertions.assertThat(exception).isInstanceOf(AbstractException::class.java)
        val abstractException = exception as AbstractException
        Assertions.assertThat(abstractException.status).isEqualTo(HttpStatus.CONFLICT)
        Assertions.assertThat(abstractException.feedbackCode).isEqualTo("model.duplicated")
    }

    @Test
    fun testModelNotFoundException_firstConstructor() {
        val exception = ModelNotFoundException()
        Assertions.assertThat(exception).isInstanceOf(AbstractException::class.java)
        val abstractException = exception as AbstractException
        Assertions.assertThat(abstractException.status).isEqualTo(HttpStatus.NOT_FOUND)
        Assertions.assertThat(abstractException.feedbackCode).isEqualTo("model.notFound")
    }

    @Test
    fun testModelNotFoundException_secondConstructor() {
        val exception = ModelNotFoundException(Any::class.java)
        Assertions.assertThat(exception).isInstanceOf(AbstractException::class.java)
        val abstractException = exception as AbstractException
        Assertions.assertThat(abstractException.status).isEqualTo(HttpStatus.NOT_FOUND)
        Assertions.assertThat(abstractException.feedbackCode).isEqualTo("model.notFound")
    }

    @Test
    fun testModelInvalidException_firstConstructor() {
        val exception = ModelInvalidException()
        Assertions.assertThat(exception).isInstanceOf(AbstractException::class.java)
        val abstractException = exception as AbstractException
        Assertions.assertThat(abstractException.status).isEqualTo(HttpStatus.BAD_REQUEST)
        Assertions.assertThat(abstractException.feedbackCode).isEqualTo("model.invalid")
    }

    @Test
    fun testModelInvalidException_secondConstructor() {
        val exception = ModelInvalidException(Any::class.java)
        Assertions.assertThat(exception).isInstanceOf(AbstractException::class.java)
        val abstractException = exception as AbstractException
        Assertions.assertThat(abstractException.status).isEqualTo(HttpStatus.BAD_REQUEST)
        Assertions.assertThat(abstractException.feedbackCode).isEqualTo("model.invalid")
    }

    @Test
    fun testPermissionException_firstConstructor() {
        val exception = PermissionException()
        Assertions.assertThat(exception).isInstanceOf(AbstractException::class.java)
        val abstractException = exception as AbstractException
        Assertions.assertThat(abstractException.status).isEqualTo(HttpStatus.FORBIDDEN)
        Assertions.assertThat(abstractException.feedbackCode).isEqualTo("permission.restricted")
    }

    @Test
    fun testPermissionException_secondConstructor() {
        val exception = PermissionException("test_message")
        Assertions.assertThat(exception).isInstanceOf(AbstractException::class.java)
        val abstractException = exception as AbstractException
        Assertions.assertThat(abstractException.status).isEqualTo(HttpStatus.FORBIDDEN)
        Assertions.assertThat(abstractException.feedbackCode).isEqualTo("permission.restricted")
    }

}
