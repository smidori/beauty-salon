package com.cct.beautysalon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@EnableJpaRepositories("com.cct.beautysalon.repositories")
@EntityScan("com.cct.beautysalon.models")
@SpringBootApplication
public class BeautySalonApplication {
	
    private static ConfigurableApplicationContext applicationContext;

	public static void main(String[] args) {
		BeautySalonApplication.applicationContext = SpringApplication.run(BeautySalonApplication.class, args);
	}

}
