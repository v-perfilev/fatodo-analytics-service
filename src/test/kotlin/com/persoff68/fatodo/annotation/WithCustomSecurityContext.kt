package com.persoff68.fatodo.annotation

import com.persoff68.fatodo.security.details.CustomUserDetails
import com.persoff68.fatodo.security.util.SecurityUtils
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContext
import org.springframework.security.test.context.support.WithSecurityContextFactory

@WithSecurityContext(factory = WithCustomSecurityContext.WithCustomSecurityContextFactory::class)
annotation class WithCustomSecurityContext(
    val id: String = "41789d72-3fa5-4019-8205-ccf44213b322",
    val username: String = "test_username",
    val jwt: String = "test_jwt",
    val authority: String = "ROLE_TEST"
) {

    class WithCustomSecurityContextFactory : WithSecurityContextFactory<WithCustomSecurityContext> {
        override fun createSecurityContext(customSecurityContext: WithCustomSecurityContext): SecurityContext {
            val securityContext = SecurityContextHolder.createEmptyContext()
            val authorityList = listOf(SimpleGrantedAuthority(customSecurityContext.authority))
            val id = SecurityUtils.getUuidFromString(customSecurityContext.id)
            val userDetails = CustomUserDetails(
                id,
                customSecurityContext.username,
                "",
                authorityList
            )
            val authentication = UsernamePasswordAuthenticationToken(
                userDetails,
                customSecurityContext.jwt,
                authorityList
            )
            securityContext.authentication = authentication
            return securityContext
        }

    }

}
