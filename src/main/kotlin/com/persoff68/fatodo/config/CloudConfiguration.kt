package com.persoff68.fatodo.config

import com.netflix.appinfo.ApplicationInfoManager
import com.netflix.appinfo.EurekaInstanceConfig
import com.netflix.appinfo.HealthCheckHandler
import com.netflix.discovery.AbstractDiscoveryClientOptionalArgs
import com.netflix.discovery.EurekaClient
import com.netflix.discovery.EurekaClientConfig
import com.netflix.discovery.shared.transport.jersey.TransportClientFactories
import com.persoff68.fatodo.config.constant.AppConstants
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.SearchStrategy
import org.springframework.cloud.netflix.eureka.CloudEurekaClient
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig
import org.springframework.cloud.netflix.eureka.InstanceInfoFactory
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

@Configuration
@EnableFeignClients(basePackages = [AppConstants.FEIGN_CLIENT_PATH])
class CloudConfiguration(
    private val context: ApplicationContext,
    private val optionalArgs: AbstractDiscoveryClientOptionalArgs<*>
) {
    // @Bean(destroyMethod = "shutdown")
    // @ConditionalOnMissingBean(value = [EurekaClient::class], search = SearchStrategy.CURRENT)
    // fun eurekaClient(
    //     manager: ApplicationInfoManager,
    //     config: EurekaClientConfig,
    //     transportClientFactories: TransportClientFactories<*>
    // ): EurekaClient {
    //     return CloudEurekaClient(manager, config, transportClientFactories, optionalArgs, context)
    // }
    //
    // @Bean
    // @ConditionalOnMissingBean(value = [ApplicationInfoManager::class], search = SearchStrategy.CURRENT)
    // fun eurekaApplicationInfoManager(config: EurekaInstanceConfig): ApplicationInfoManager {
    //     val instanceInfo = InstanceInfoFactory().create(config)
    //     return ApplicationInfoManager(config, instanceInfo)
    // }
    //
    // @Bean
    // @ConditionalOnMissingBean(value = [EurekaRegistration::class])
    // fun eurekaRegistration(
    //     eurekaClient: EurekaClient,
    //     instanceConfig: CloudEurekaInstanceConfig,
    //     applicationInfoManager: ApplicationInfoManager,
    //     @Autowired(required = false) healthCheckHandler: ObjectProvider<HealthCheckHandler>
    // ): EurekaRegistration {
    //     return EurekaRegistration
    //         .builder(instanceConfig)
    //         .with(applicationInfoManager)
    //         .with(eurekaClient)
    //         .with(healthCheckHandler)
    //         .build()
    // }
}
