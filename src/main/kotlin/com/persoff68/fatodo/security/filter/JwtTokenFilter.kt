package com.persoff68.fatodo.security.filter

import com.persoff68.fatodo.config.AppProperties
import com.persoff68.fatodo.security.jwt.JwtTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtTokenFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val appProperties: AppProperties
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = getJwtFromRequest(request)
        val hasJwt = StringUtils.hasText(jwt) && jwtTokenProvider.validateJwt(jwt!!)

        if (hasJwt) {
            val authentication: UsernamePasswordAuthenticationToken = jwtTokenProvider.getAuthenticationFromJwt(jwt!!)
            val authenticationDetails = WebAuthenticationDetailsSource().buildDetails(request)
            authentication.details = authenticationDetails
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val authHeader = appProperties.auth.authorizationHeader
        val authPrefix = appProperties.auth.authorizationPrefix
        var jwt: String? = null
        val headerList = request.headerNames.toList().map { it.lowercase() }
        val hasHeader = headerList.contains(authHeader.lowercase())
        if (hasHeader) {
            val bearerToken = request.getHeader(authHeader)
            val hasToken = StringUtils.hasText(bearerToken) && bearerToken.startsWith(authPrefix)
            jwt = if (hasToken) bearerToken.substring(authPrefix.length) else null
        }
        return jwt
    }
}
