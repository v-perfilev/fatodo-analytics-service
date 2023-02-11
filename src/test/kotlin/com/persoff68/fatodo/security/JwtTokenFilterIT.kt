package com.persoff68.fatodo.security

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class JwtTokenFilterIT(@Autowired val mvc: MockMvc) {
    @Value("\${test.jwt.user}")
    lateinit var userJwt: String

    @Value("\${test.jwt.invalid-expired}")
    lateinit var invalidExpiredJwt: String

    @Value("\${test.jwt.invalid-format}")
    lateinit var invalidFormatJwt: String

    @Value("\${test.jwt.invalid-wrong-token}")
    lateinit var invalidWrongTokenJwt: String

    @Value("\${test.jwt.invalid-wrong-uuid}")
    lateinit var invalidWrongUuidJwt: String

    @Test
    fun testSuccessfulAuthorization() {
        mvc.get("/") {
            header("Authorization", "Bearer $userJwt")
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun testWrongPrefix() {
        mvc.get("/") {
            header("Authorization", "Prefix $userJwt")
        }.andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun testNoHeader() {
        mvc.get("/").andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun testInvalidExpiredJwt() {
        mvc.get("/") {
            header("Authorization", "Bearer $invalidExpiredJwt")
        }.andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun testInvalidEmptyJwt() {
        mvc.get("/") {
            header("Authorization", "Bearer $invalidFormatJwt")
        }.andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun testInvalidWrongTokenJwt() {
        mvc.get("/") {
            header("Authorization", "Bearer $invalidWrongTokenJwt")
        }.andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun testInvalidWrongUuidJwt() {
        mvc.get("/") {
            header("Authorization", "Bearer $invalidWrongUuidJwt")
        }.andExpect {
            status { isUnauthorized() }
        }
    }
}
