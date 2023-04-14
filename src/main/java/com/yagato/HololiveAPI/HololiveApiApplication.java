package com.yagato.HololiveAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class HololiveApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HololiveApiApplication.class, args);
	}

}
