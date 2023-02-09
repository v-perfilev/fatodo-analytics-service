package com.persoff68.fatodo.config.constant

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class AuthorityTypeTest {
    @Test
    fun testEnum() {
        Assertions.assertThat(AuthorityType.ADMIN.getValue()).isEqualTo("ROLE_ADMIN")
        Assertions.assertThat(AuthorityType.SYSTEM.getValue()).isEqualTo("ROLE_SYSTEM")
        Assertions.assertThat(AuthorityType.USER.getValue()).isEqualTo("ROLE_USER")
    }

    @Test
    fun testContains() {
        val isTrue: Boolean = AuthorityType.contains("ROLE_SYSTEM")
        Assertions.assertThat(isTrue).isTrue
        val isFalse: Boolean = AuthorityType.contains("ROLE_NOT_EXISTS")
        Assertions.assertThat(isFalse).isFalse
    }
}
