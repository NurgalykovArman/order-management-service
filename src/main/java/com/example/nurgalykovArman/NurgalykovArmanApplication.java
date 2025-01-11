package com.example.nurgalykovArman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class NurgalykovArmanApplication {

	public static void main(String[] args) {
		SpringApplication.run(NurgalykovArmanApplication.class, args);
	}

}
