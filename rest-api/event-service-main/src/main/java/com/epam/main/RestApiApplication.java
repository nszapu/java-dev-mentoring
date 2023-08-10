package com.epam.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = {
        "com.epam.event.repository",
        "com.epam.event.service.api",
        "com.epam.event.service.dto",
        "com.epam.event.service.impl",
        "com.epam.event.service.rest"
})
@SpringBootApplication
public class RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }

}
