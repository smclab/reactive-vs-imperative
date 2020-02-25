package it.smc.aperitech.reactiveweather;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReactiveWeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveWeatherApplication.class, args);
	}

	@Bean
	Jackson2ObjectMapperBuilderCustomizer
		createJackson2ObjectMapperBuilderCustomizer() {
		return jacksonObjectMapperBuilder ->
			jacksonObjectMapperBuilder
				.modules(new JavaTimeModule())
				.featuresToDisable(
					SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

}
