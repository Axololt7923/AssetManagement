package com.axololt.assetmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {
    @Bean
    public String getAccessKey() {
        return System.getenv("ACCESS_KEY");
    }
}
