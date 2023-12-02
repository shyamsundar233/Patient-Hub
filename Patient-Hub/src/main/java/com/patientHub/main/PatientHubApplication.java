package com.patientHub.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
public class PatientHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientHubApplication.class, args);
	}

}
