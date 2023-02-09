package com.persoff68.fatodo.security

import com.persoff68.fatodo.config.AppProperties
import com.persoff68.fatodo.config.constant.AppConstants
import com.persoff68.fatodo.security.details.CustomUserDetails
import com.persoff68.fatodo.security.jwt.JwtTokenProvider
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.UUID

@ExtendWith(MockKExtension::class)
class JwtTokenProviderTest {
    private lateinit var jwtTokenProvider: JwtTokenProvider
    private lateinit var appProperties: AppProperties
    private lateinit var auth: AppProperties.Auth

    @BeforeEach
    fun setup() {
        appProperties = mockk(relaxed = true)
        auth = mockk(relaxed = true)
        every { appProperties.auth } returns auth
        every { auth.tokenSecret } returns "4323B829EF1EEF14E324241FB72E6"
        jwtTokenProvider = JwtTokenProvider(appProperties)
    }

    @Test
    fun testGetAuthenticationFromJwt() {
        val jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiNDQ2Y2I5OS01ZGQ1LTQ4YTgtOWNjMS1kMWI4OTc5NzY4ZmMiLCJ1c2VybmFtZSI6InRlc3RfdGVzdCIsImF1dGhvcml0aWVzIjoiUk9MRV9URVNUIiwiaWF0IjowLCJleHAiOjMyNTAzNjc2NDAwfQ.AxcHnejWG4Y_edm_ymjO6U92UPKoZTn_a5kLLv4j_M4bvGkCOmMigLET6a9F4DpbVW2zUlnXNyvVY_KpxadEQg"
        val id = UUID.fromString("b446cb99-5dd5-48a8-9cc1-d1b8979768fc")
        val username = "test_test"
        val authorityList = listOf(SimpleGrantedAuthority("ROLE_TEST"))
        val userDetails = CustomUserDetails(id, username, "", authorityList)
        val authentication = UsernamePasswordAuthenticationToken(userDetails, jwt, authorityList)
        val resultAuthentication = jwtTokenProvider.getAuthenticationFromJwt(jwt)
        assertThat(resultAuthentication).isEqualTo(authentication)
    }

    @Test
    fun testCreateSystemJwtAndValidateJwt() {
        val jwt = jwtTokenProvider.createSystemJwt()
        val authentication = jwtTokenProvider.getAuthenticationFromJwt(jwt)
        val isValid = jwtTokenProvider.validateJwt(jwt)
        val hasSystemAuthority = authentication.authorities
            .stream()
            .map { it.authority }
            .anyMatch { it == AppConstants.SYSTEM_AUTHORITY }
        assertThat(isValid).isTrue
        assertThat(hasSystemAuthority).isTrue
    }
}
