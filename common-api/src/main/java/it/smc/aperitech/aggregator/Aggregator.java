package it.smc.aperitech.aggregator;

import it.smc.aperitech.dto.CityContainerModel;

import java.util.Collection;

/**
 * @author Mauro Celani
 */
public interface Aggregator {

	CityContainerModel getCityWeather(
			long cityId)
		throws Exception;

	Collection<CityContainerModel> getCityWeather(
			String cityName)
		throws Exception;

	Collection<CityContainerModel.City> getCityByName(
			String cityName)
		throws Exception;

	Collection<CityContainerModel.City> getCityByNameLike(
			String cityName)
		throws Exception;

	Collection<CityContainerModel.City> getCityByNameILike(
			String cityName)
		throws Exception;
}
