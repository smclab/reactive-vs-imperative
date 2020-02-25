package it.smc.aperitech.aggregator;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Contract;
import feign.jaxrs.JAXRSContract;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class AggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AggregatorApplication.class, args);
	}

	@Bean
	public Contract createJaxRsContract() {
		return new JAXRSContract();
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer createCustomModule() {

		return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
			.modules(new JavaTimeModule())
			.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

}
