package com.epam.core.cofig;

import com.epam.core.Application;
import com.epam.core.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ClassLoader classLoader() {
        return Application.class.getClassLoader();
    }

    @Bean
    public EventService eventService() {
        return new EventService();
    }
}
