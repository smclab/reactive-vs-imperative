package it.smc.aperitech.reactivecity.repository;

import it.smc.aperitech.reactivecity.model.City;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * @author Mauro Celani
 */
public interface CityRepository
	extends R2dbcRepository<City, Long> {

}
