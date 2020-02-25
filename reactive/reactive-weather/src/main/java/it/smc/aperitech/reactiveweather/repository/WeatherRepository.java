package it.smc.aperitech.reactiveweather.repository;

import it.smc.aperitech.reactiveweather.model.Weather;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author Cristian Bianco
 */
public interface WeatherRepository extends
	ReactiveMongoRepository<Weather, Long> {
}
