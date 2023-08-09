package com.epam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


@PropertySource("file:src/main/resources/application-dev.properties")
@SpringBootApplication
public class RestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}

}
