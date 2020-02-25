package it.smc.aperitech.aggregator.rest;

import it.smc.aperitech.aggregator.Aggregator;
import it.smc.aperitech.aggregator.exception.NoSuchCityException;
import it.smc.aperitech.aggregator.proxy.CityProxyImpl;
import it.smc.aperitech.aggregator.proxy.WeatherProxyImpl;
import it.smc.aperitech.dto.CityContainerModel;
import it.smc.aperitech.dto.CityContainerModel.City;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Mauro Celani
 */
@RestController
@RequiredArgsConstructor
public class AggregatorController implements Aggregator {

	@Override
	@GetMapping("/city-weather/{cityId}")
	public CityContainerModel getCityWeather(@PathVariable long cityId)
		throws Exception {

		Optional<City> city = _cityProxy.getCity(cityId);

		if (city.isEmpty()) {
			throw new NoSuchCityException(
				"The city with id '" + cityId + "' wasn't found.");
		}

		return CityContainerModel.of(
			city.get(), _weatherProxy.getDatasByCityId(cityId));
	}

	@Override
	@GetMapping("/city-name-weather")
	public Collection<CityContainerModel> getCityWeather(
			@RequestParam String cityName)
		throws Exception {

		Collection<City> cities = _cityProxy.getCityByName(cityName);

		if (cities.isEmpty()) {
			throw new NoSuchCityException(
				"The city with name '" + cityName + "' wasn't found.");
		}

		return cities.stream()
			.map(city -> CityContainerModel.of(
				city, _weatherProxy.getDatasByCityId(city.getId())))
			.collect(Collectors.toList());
	}

	@Override
	@GetMapping("/city-by-name")
	public Collection<City> getCityByName(
			@RequestParam String cityName)
		throws Exception {

		return _cityProxy.getCityByName(cityName);
	}

	@Override
	@GetMapping("/city-by-name-like")
	public Collection<City> getCityByNameLike(
			@RequestParam String cityName)
		throws Exception {

		return _cityProxy.getCityByNameLike(cityName);
	}

	@Override
	@GetMapping("/city-by-name-ilike")
	public Collection<City> getCityByNameILike(
			@RequestParam String cityName)
		throws Exception {

		return _cityProxy.getCityByNameILike(cityName);
	}
/*
	@GetMapping("/datas-between-date-from")
	public List<CityContainerModel.Data> findByDtBetweenOrderByFromDt(
			@RequestParam String from)
		throws Exception {

		return _weatherProxy.findByDtBetweenOrderByFromDt(from);
	}

	@GetMapping("/datas-between-date-to")
	public List<CityContainerModel.Data> findByDtBetweenOrderByToDt(
			@RequestParam String to)
		throws Exception {

		return _weatherProxy.findByDtBetweenOrderByToDt(to);
	}

	@GetMapping("/datas-between-date-from")
	public List<CityContainerModel.Data> findByDtBetweenOrderByFromDt(
			@RequestParam String from,
			@RequestParam Long cityId)
		throws Exception {

		return _weatherProxy.findByDtBetweenOrderByFromDt(from, cityId);
	}

	@GetMapping("/datas-between-date-to")
	public List<CityContainerModel.Data> findByDtBetweenOrderByToDt(
			@RequestParam String to,
			@RequestParam Long cityId)
		throws Exception {

		return _weatherProxy.findByDtBetweenOrderByToDt(to, cityId);
	}


	@GetMapping("/datas-between-date-from")
	public List<CityContainerModel.Data> findByDtBetweenOrderByFromDt(
			@RequestParam String from,
			@RequestParam String orderByName,
			@RequestParam String orderByType)
		throws Exception {

		return _weatherProxy.findByDtBetweenOrderByFromDt(
			from, orderByName, orderByType);
	}

	@GetMapping("/datas-between-date-to")
	public List<CityContainerModel.Data> findByDtBetweenOrderByToDt(
			@RequestParam String to,
			@RequestParam String orderByName,
			@RequestParam String orderByType)
		throws Exception {

		return _weatherProxy.findByDtBetweenOrderByToDt(
			to, orderByName, orderByType);
	}

	@GetMapping("/datas-between-date-from")
	public List<CityContainerModel.Data> findByDtBetweenOrderByFromDt(
			@RequestParam String from,
			@RequestParam Long cityId,
			@RequestParam String orderByName,
			@RequestParam String orderByType)
		throws Exception {

		return _weatherProxy.findByDtBetweenOrderByFromDt(
			from, cityId, orderByName, orderByType);
	}

	@GetMapping("/datas-between-date-to")
	public List<CityContainerModel.Data> findByDtBetweenOrderByToDt(
			@RequestParam String to,
			@RequestParam Long cityId,
			@RequestParam String orderByName,
			@RequestParam String orderByType)
		throws Exception{

		return _weatherProxy.findByDtBetweenOrderByToDt(
			to, cityId, orderByName, orderByType);
	}

	@GetMapping("/datas-between-date")
	public List<CityContainerModel.Data> findByDtBetweenOrderByDt(
			@RequestParam String from,
			@RequestParam String to,
			@RequestParam Long cityId)
		throws Exception {

		return _weatherProxy.findByDtBetweenOrderByDt(from, to, cityId);
	}

	@GetMapping("/datas-between-date")
	public List<CityContainerModel.Data> findByDtBetweenOrderByDt(
			@RequestParam String from,
			@RequestParam String to,
			@RequestParam Long cityId,
			@RequestParam String orderByName,
			@RequestParam String orderByType)
		throws Exception {

		return _weatherProxy.findByDtBetweenOrderByDt(
			from, to, cityId, orderByName, orderByType);
	}
*/
	private final CityProxyImpl _cityProxy;

	private final WeatherProxyImpl _weatherProxy;

}
