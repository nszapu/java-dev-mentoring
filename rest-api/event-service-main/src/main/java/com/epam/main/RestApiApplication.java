package com.epam.main;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@OpenAPIDefinition(info = @Info(
        title = "Sample Event API",
        version = "1.0.0",
        description = "This is a sample API for events"
))
@ComponentScan(basePackages = {
        "com.epam.event.repository",
        "com.epam.event.service.api",
        "com.epam.event.service.dto",
        "com.epam.event.service.impl",
        "com.epam.event.service.rest",
        "com.epam.main.config",
        "com.epam.event.service.rest.util",
        "com.epam.event.service.impl.util"
})
@SpringBootApplication
public class RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }

}
