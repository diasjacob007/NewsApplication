package com.stackroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * This is the main starter project meant for including the two sub-modules
 * viz the AuthenticatonService module and the Server module 
 */
@SpringBootApplication
public class NewsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsAppApplication.class, args);
	}

}

