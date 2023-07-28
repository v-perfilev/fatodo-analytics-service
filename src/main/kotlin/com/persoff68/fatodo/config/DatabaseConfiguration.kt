package com.persoff68.fatodo.config

import com.persoff68.fatodo.config.constant.AppConstants
import liquibase.integration.spring.SpringLiquibase
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.sql.SQLException
import java.sql.Timestamp
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(basePackages = [AppConstants.REPOSITORY_PATH])
@EnableJpaAuditing(auditorAwareRef = "securityAuditorAware")
@EnableTransactionManagement
class DatabaseConfiguration(private val appProperties: AppProperties, private val dataSource: DataSource) {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    @Bean
    fun liquibase(): SpringLiquibase {
        removeExpiredLocks()
        val liquibase = SpringLiquibase()
        liquibase.changeLog = "classpath:db/master.xml"
        liquibase.dataSource = dataSource
        return liquibase
    }

    private fun removeExpiredLocks() {
        val timeoutInMillis = appProperties.db.liquibaseLockTimeoutSec * 1000
        val lastLockTime = Timestamp(System.currentTimeMillis() - timeoutInMillis)
        val query = "DELETE FROM DATABASECHANGELOGLOCK WHERE LOCKED=true AND LOCKGRANTED<'$lastLockTime'"
        try {
            dataSource.connection.use { connection ->
                val statement = connection.createStatement()
                val updateCount = statement.executeUpdate(query)
                if (updateCount > 0) {
                    logger.warn("Liquibase locks removed: $updateCount")
                } else {
                    logger.info("No liquibase locks removed")
                }
            }
        } catch (e: SQLException) {
            logger.error("Remove liquibase lock threw and exception. ${e.message}")
        }
    }
}
