package com.persoff68.fatodo.security.jwt

import com.persoff68.fatodo.config.AppProperties
import com.persoff68.fatodo.config.constant.AppConstants
import com.persoff68.fatodo.security.details.CustomUserDetails
import com.persoff68.fatodo.security.util.SecurityUtils
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import mu.KotlinLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.Date
import java.util.UUID
import java.util.concurrent.TimeUnit

@Component
class JwtTokenProvider(private val appProperties: AppProperties) {
    companion object {
        private const val USERNAME_KEY = "username"
        private const val AUTHORITY_KEY = "authorities"

        private val logger = KotlinLogging.logger {}
    }

    fun getAuthenticationFromJwt(jwt: String): UsernamePasswordAuthenticationToken {
        val claims = getClaimsFromJwt(jwt)
        val id = SecurityUtils.getUuidFromString(claims.subject)
        val username = claims[USERNAME_KEY].toString()
        val authorityList = claims[AUTHORITY_KEY].toString()
            .split(",")
            .map { SimpleGrantedAuthority(it) }
        val userDetails = CustomUserDetails(id, username, "", authorityList)
        return UsernamePasswordAuthenticationToken(userDetails, jwt, authorityList)
    }

    fun createSystemJwt(): String {
        val params = JwtParams(
            UUID.fromString(AppConstants.SYSTEM_ID),
            AppConstants.SYSTEM_USERNAME,
            AppConstants.SYSTEM_AUTHORITY,
            Date(),
            Date(Date().time + TimeUnit.SECONDS.toMillis(AppConstants.SYSTEM_TOKEN_EXPIRATION_SEC))
        )
        return buildJwt(params)
    }

    fun validateJwt(jwt: String): Boolean {
        try {
            val tokenSecret = appProperties.auth.tokenSecret
            val id = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(jwt).body.subject
            SecurityUtils.getUuidFromString(id)
            return true
        } catch (ex: SignatureException) {
            logger.error("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            logger.error("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            logger.error("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            logger.error("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            logger.error("JWT claims string not valid")
        }
        return false
    }

    private fun getClaimsFromJwt(jwt: String): Claims {
        val tokenSecret = appProperties.auth.tokenSecret
        return Jwts.parser()
            .setSigningKey(tokenSecret)
            .parseClaimsJws(jwt)
            .body
    }

    private fun buildJwt(params: JwtParams): String {
        val tokenSecret = appProperties.auth.tokenSecret
        return Jwts.builder()
            .setSubject(params.id.toString())
            .claim(USERNAME_KEY, params.username)
            .claim(AUTHORITY_KEY, params.authorities)
            .setIssuedAt(params.nowDate)
            .setExpiration(params.expirationDate)
            .signWith(SignatureAlgorithm.HS512, tokenSecret)
            .compact()
    }

    private class JwtParams(
        var id: UUID,
        var username: String,
        var authorities: String,
        var nowDate: Date,
        var expirationDate: Date,
    )
}
