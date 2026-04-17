package com.axololt.assetmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class EnvConfig {
    @Bean
    public String getAccessKey() {
        return Dotenv.load().get("ACCESS_KEY");
    }
}
