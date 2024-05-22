package org.nttdata.credits_service.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "org.nttdata.credits_service.service.feign")
public class FeignClientsConfig {
}
