package com.persoff68.fatodo.security

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

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
        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer $userJwt")
        mvc.perform(MockMvcRequestBuilders.get("/").headers(headers))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun testWrongPrefix() {
        val headers = HttpHeaders()
        headers.add("Authorization", "" + userJwt)
        mvc.perform(MockMvcRequestBuilders.get("/").headers(headers))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun testNoHeader() {
        mvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun testInvalidExpiredJwt() {
        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer $invalidExpiredJwt")
        mvc.perform(MockMvcRequestBuilders.get("/").headers(headers))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun testInvalidEmptyJwt() {
        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer $invalidFormatJwt")
        mvc.perform(MockMvcRequestBuilders.get("/").headers(headers))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun testInvalidWrongTokenJwt() {
        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer $invalidWrongTokenJwt")
        mvc.perform(MockMvcRequestBuilders.get("/").headers(headers))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun testInvalidWrongUuidJwt() {
        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer $invalidWrongUuidJwt")
        mvc.perform(MockMvcRequestBuilders.get("/").headers(headers))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }
}
