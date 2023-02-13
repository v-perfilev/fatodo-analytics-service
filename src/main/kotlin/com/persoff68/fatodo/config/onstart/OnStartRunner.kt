package com.persoff68.fatodo.config.onstart

import mu.KotlinLogging
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.web.context.WebServerInitializedEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.util.Objects

@Component
class OnStartRunner(
    private val env: Environment
) : ApplicationRunner, ApplicationListener<WebServerInitializedEvent> {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private var appName: String? = null
    private var protocol: String? = null
    private var host: String? = null
    private var port: String? = null
    private var profiles: Array<String>? = null

    override fun onApplicationEvent(event: WebServerInitializedEvent) {
        try {
            appName = env.getProperty("spring.application.name")
            protocol = "http"
            host = "localhost"
            port = env.getProperty("server.port")
            profiles = if (env.activeProfiles.isNotEmpty()) env.activeProfiles else env.defaultProfiles
        } catch (e: Exception) {
            // skip on start
        }
    }

    override fun run(args: ApplicationArguments) {
        val allNotNull = listOf(appName, protocol, host, port, profiles).stream().allMatch { Objects.nonNull(it) }
        if (allNotNull) logCommonInfo()
    }

    private fun logCommonInfo() {
        logger.info(
            """
                    Application $appName is running!
                    Access URL: $protocol://$host:$port
                    Profile(s): $profiles.joinToString(separator = ", ")
                """
        )
    }
}
