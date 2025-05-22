package com.ssafy.travelquest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TravelQuestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelQuestApplication.class, args);
	}

}
