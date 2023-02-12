package com.persoff68.fatodo.config

import com.persoff68.fatodo.security.filter.JwtTokenFilter
import com.persoff68.fatodo.security.filter.SecurityLocalFilter
import com.persoff68.fatodo.security.filter.SecurityProblemSupport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val securityProblemSupport: SecurityProblemSupport,
    private val jwtTokenFilter: JwtTokenFilter,
    private val securityLocaleFilter: SecurityLocalFilter
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            cors { }
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
            httpBasic { disable() }
            exceptionHandling {
                authenticationEntryPoint = securityProblemSupport
                accessDeniedHandler = securityProblemSupport
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(jwtTokenFilter)
            addFilterAfter<UsernamePasswordAuthenticationFilter>(securityLocaleFilter)
            authorizeRequests {
                authorize("/management/**", permitAll)
                authorize("/v3/api-docs/**", permitAll)
                authorize("/swagger-ui/**", permitAll)
                authorize("/swagger-ui.html", permitAll)
                authorize(anyRequest, authenticated)
            }
        }
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()
        config.addAllowedOriginPattern("*")
        config.addAllowedMethod("*")
        config.addAllowedHeader("*")
        config.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }
}
