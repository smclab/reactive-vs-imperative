package it.smc.aperitech.reactivecity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.time.LocalDateTime;

@SpringBootApplication
public class ReactiveCityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveCityApplication.class, args);
	}

}
