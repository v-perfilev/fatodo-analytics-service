package com.persoff68.fatodo

import com.persoff68.fatodo.config.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [GsonAutoConfiguration::class])
@EnableConfigurationProperties(AppProperties::class)
class FatodoAnalyticsServiceApplication

fun main(args: Array<String>) {
    runApplication<FatodoAnalyticsServiceApplication>(*args)
}
