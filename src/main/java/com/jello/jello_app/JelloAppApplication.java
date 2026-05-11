package com.jello.jello_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JelloAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JelloAppApplication.class, args);
	}

}
