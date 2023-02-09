package com.persoff68.fatodo.client.configuration

import com.persoff68.fatodo.config.AppProperties
import com.persoff68.fatodo.security.exception.UnauthorizedException
import com.persoff68.fatodo.security.util.SecurityUtils
import feign.RequestInterceptor

class FeignAuthConfiguration {
    fun requestInterceptor(appProperties: AppProperties): RequestInterceptor {
        return RequestInterceptor { requestTemplate ->
            val jwt = SecurityUtils.getCurrentJwt() ?: throw UnauthorizedException()
            val header = appProperties.auth.authorizationHeader
            val value = appProperties.auth.authorizationPrefix + " " + jwt
            requestTemplate.header(header, value)
        }
    }
}
