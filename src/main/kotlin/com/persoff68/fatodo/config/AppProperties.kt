package com.persoff68.fatodo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty

@ConfigurationProperties(prefix = "app")
data class AppProperties(
    @NestedConfigurationProperty val common: Common = Common(),
    @NestedConfigurationProperty val auth: Auth = Auth(),
    @NestedConfigurationProperty val db: DB = DB()
) {
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

    class DB {
        var liquibaseLockTimeoutSec: Long = 300
    }
}
