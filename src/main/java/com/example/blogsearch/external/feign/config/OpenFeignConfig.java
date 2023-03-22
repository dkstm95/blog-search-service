package com.example.blogsearch.external.feign.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.example.blogsearch.external.feign")
public class OpenFeignConfig {

    private static final int PERIOD = 1000;
    private static final int MAX_PERIOD = 2000;
    private static final int MAX_ATTEMPTS = 1;

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(PERIOD, MAX_PERIOD, MAX_ATTEMPTS);
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
