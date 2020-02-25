package it.smc.aperitech.city;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityApplication.class, args);
	}

}
