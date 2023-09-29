package com.teamback.wise;

import com.teamback.wise.security.JWTConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JWTConfigProperties.class)
@SpringBootApplication
public class WiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(WiseApplication.class, args);
	}

}
