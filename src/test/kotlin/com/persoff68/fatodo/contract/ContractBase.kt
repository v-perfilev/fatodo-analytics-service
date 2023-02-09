package com.persoff68.fatodo.contract

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMessageVerifier
abstract class ContractBase(private val context: WebApplicationContext) {
    @BeforeEach
    fun setup() {
        RestAssuredMockMvc.webAppContextSetup(context)
    }
}
