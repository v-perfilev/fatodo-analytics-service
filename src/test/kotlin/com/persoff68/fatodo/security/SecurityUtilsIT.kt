package com.persoff68.fatodo.security

import com.persoff68.fatodo.annotation.WithCustomSecurityContext
import com.persoff68.fatodo.security.util.SecurityUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
class SecurityUtilsIT {
    @Test
    @WithAnonymousUser
    fun testGetCurrentId_ifAnonymous() {
        val id = SecurityUtils.getCurrentId()
        assertThat(id).isNull()
    }

    @Test
    @WithCustomSecurityContext
    fun testGetCurrentId_ifAuthorized() {
        val id = SecurityUtils.getCurrentId()
        assertThat(id)
            .isNotNull
            .isEqualTo(UUID.fromString("41789d72-3fa5-4019-8205-ccf44213b322"))
    }

    @Test
    @WithAnonymousUser
    fun testGetCurrentUsername_ifAnonymous() {
        val username = SecurityUtils.getCurrentUsername()
        assertThat(username).isNull()
    }

    @Test
    @WithCustomSecurityContext
    fun testGetCurrentUsername_ifAuthorized() {
        val username = SecurityUtils.getCurrentUsername()
        assertThat(username)
            .isNotNull
            .isEqualTo("test_username")
    }

    @Test
    @WithAnonymousUser
    fun testGetCurrentJwt_ifAnonymous() {
        val jwt = SecurityUtils.getCurrentJwt()
        assertThat(jwt).isNull()
    }

    @Test
    @WithCustomSecurityContext
    fun testGetCurrentJwt_ifAuthorized() {
        val jwt = SecurityUtils.getCurrentJwt()
        assertThat(jwt)
            .isNotNull
            .contains("test_jwt")
    }

    @Test
    @WithAnonymousUser
    fun testGetCurrentAuthoritySet_ifAnonymous() {
        val authoritySet = SecurityUtils.getCurrentAuthoritySet()
        assertThat(authoritySet).isNull()
    }

    @Test
    @WithCustomSecurityContext
    fun testGetCurrentAuthoritySet_ifAuthorized() {
        val authoritySet = SecurityUtils.getCurrentAuthoritySet()
        assertThat(authoritySet)
            .isNotNull
            .contains(SimpleGrantedAuthority("ROLE_TEST"))
            .hasSize(1)
    }
}
