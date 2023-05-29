package com.reticket.reticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class ReticketApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReticketApplication.class, args);
	}

}
