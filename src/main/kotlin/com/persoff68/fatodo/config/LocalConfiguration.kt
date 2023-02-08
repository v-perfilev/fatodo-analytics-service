package com.persoff68.fatodo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.FixedLocaleResolver
import java.util.*

@Configuration
class LocalConfiguration {

    @Bean
    fun localeResolver(): LocaleResolver {
        val localeResolver = FixedLocaleResolver()
        localeResolver.setDefaultLocale(Locale.ENGLISH)
        return localeResolver
    }

}
