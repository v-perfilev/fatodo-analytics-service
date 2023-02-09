package com.persoff68.fatodo.security.util

import com.persoff68.fatodo.security.details.CustomUserDetails
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import java.util.UUID

object SecurityUtils {
    fun getUuidFromString(id: String): UUID {
        return UUID.fromString(id)
    }

    fun getCurrentId(): UUID? {
        val authentication = getCurrentAuthentication()
        return fetchIdFromAuthentication(authentication!!)
    }

    fun getCurrentUsername(): String? {
        val authentication = getCurrentAuthentication()
        return fetchUsernameFromAuthentication(authentication!!)
    }

    fun getCurrentAuthoritySet(): Set<GrantedAuthority>? {
        val authentication = getCurrentAuthentication()
        return fetchAuthoritiesFromAuthentication(authentication!!)
    }

    fun getCurrentJwt(): String? {
        val authentication = getCurrentAuthentication()
        return fetchJwtFromAuthentication(authentication!!)
    }

    private fun fetchIdFromAuthentication(authentication: Authentication): UUID? {
        var id: UUID? = null
        if (authentication is UsernamePasswordAuthenticationToken) {
            val userDetails = authentication.getPrincipal() as CustomUserDetails
            id = userDetails.id
        }
        return id
    }

    private fun fetchUsernameFromAuthentication(authentication: Authentication): String? {
        var username: String? = null
        if (authentication is UsernamePasswordAuthenticationToken) {
            val userDetails = authentication.getPrincipal() as CustomUserDetails
            username = userDetails.username
        }
        return username
    }

    private fun fetchAuthoritiesFromAuthentication(authentication: Authentication): Set<GrantedAuthority>? {
        var authoritySet: Set<GrantedAuthority>? = null
        if (authentication is UsernamePasswordAuthenticationToken) {
            authoritySet = HashSet(authentication.getAuthorities())
        }
        return authoritySet
    }

    private fun fetchJwtFromAuthentication(authentication: Authentication): String? {
        var jwt: String? = null
        if (authentication is UsernamePasswordAuthenticationToken) {
            jwt = authentication.getCredentials() as String
        }
        return jwt
    }

    private fun getCurrentAuthentication(): Authentication? {
        val securityContext = SecurityContextHolder.getContext()
        return securityContext.authentication
    }
}
