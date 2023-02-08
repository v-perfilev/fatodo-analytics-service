package com.persoff68.fatodo.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
class AppProperties {

    lateinit var common: Common
    lateinit var auth: Auth

    class Common {
        var baseUrl: String = ""
        var apiUrl: String = ""
    }

    class Auth {
        var authorizationHeader: String = ""
        var authorizationPrefix: String = ""
        var tokenSecret: String = ""
        var tokenExpirationSec: Long = 0
        var captchaSecret: String = ""
    }
}
