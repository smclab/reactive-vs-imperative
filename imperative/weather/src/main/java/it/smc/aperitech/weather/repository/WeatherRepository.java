package it.smc.aperitech.weather.repository;

import it.smc.aperitech.weather.model.Weather;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Mauro Celani
 */
public interface WeatherRepository extends MongoRepository<Weather, Long> {
}
