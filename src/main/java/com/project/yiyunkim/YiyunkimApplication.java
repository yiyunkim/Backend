package com.project.yiyunkim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class YiyunkimApplication {

	public static void main(String[] args) {
		SpringApplication.run(YiyunkimApplication.class, args);
	}

}
