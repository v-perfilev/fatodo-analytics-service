package com.persoff68.fatodo.client.configuration

import com.persoff68.fatodo.config.AppProperties
import com.persoff68.fatodo.security.jwt.JwtTokenProvider
import feign.RequestInterceptor

class FeignSystemConfiguration {
    fun requestInterceptor(appProperties: AppProperties, jwtTokenProvider: JwtTokenProvider): RequestInterceptor {
        return RequestInterceptor { requestTemplate ->
            val jwt = jwtTokenProvider.createSystemJwt()
            val header = appProperties.auth.authorizationHeader
            val value = appProperties.auth.authorizationPrefix + " " + jwt
            requestTemplate.header(header, value)
        }
    }
}
