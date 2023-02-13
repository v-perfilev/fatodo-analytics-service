package com.persoff68.fatodo.config

import com.persoff68.fatodo.config.constant.AppConstants
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = [AppConstants.FEIGN_CLIENT_PATH])
class CloudConfiguration
