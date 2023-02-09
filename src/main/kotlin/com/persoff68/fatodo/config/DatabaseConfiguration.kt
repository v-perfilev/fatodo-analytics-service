package com.persoff68.fatodo.config

import com.persoff68.fatodo.config.constant.AppConstants
import liquibase.integration.spring.SpringLiquibase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(basePackages = [AppConstants.REPOSITORY_PATH])
@EnableJpaAuditing(auditorAwareRef = "securityAuditorAware")
@EnableTransactionManagement
class DatabaseConfiguration(private val dataSource: DataSource) {
    @Bean
    fun liquibase(): SpringLiquibase {
        val liquibase = SpringLiquibase()
        liquibase.changeLog = "classpath:db/master.xml"
        liquibase.dataSource = dataSource
        return liquibase
    }
}
