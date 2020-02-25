package it.smc.aperitech.reactive.aggregator;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ReactiveAggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveAggregatorApplication.class, args);
	}

	@Bean
	WebClient create(
		ReactorLoadBalancerExchangeFilterFunction
			loadBalancerExchangeFilterFunction) {
		return WebClient
			.builder()
			.filter(loadBalancerExchangeFilterFunction)
			.build();
	}

	@Bean
	Jackson2ObjectMapperBuilderCustomizer createJavaTimeModule() {
		return jacksonObjectMapperBuilder ->
			jacksonObjectMapperBuilder
				.modules(new JavaTimeModule())
				.featuresToDisable(
					SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

}
