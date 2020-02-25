package it.smc.aperitech.city.repository;

import it.smc.aperitech.city.model.City;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

/**
 * @author Mauro Celani
 */
public interface CityRepository
	extends PagingAndSortingRepository<City, Long> {

	Collection<City> findByName(String name);

	Collection<City> findByNameLike(String name);

	Collection<City> findByNameLikeIgnoreCase(String name);

}
