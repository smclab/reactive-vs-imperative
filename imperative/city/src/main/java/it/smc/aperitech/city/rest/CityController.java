package it.smc.aperitech.city.rest;

import it.smc.aperitech.city.model.City;
import it.smc.aperitech.city.repository.CityRepository;
import it.smc.aperitech.dto.CityContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class CityController {

	@PostMapping("/city")
	public void addCity(@RequestBody CityContainer.City city) throws Exception {

		_cityRepository.save(convertCityToModel(city));
	}


	@PostMapping("/cities")
	public void addCities(@RequestBody List<CityContainer.City> cities)
		throws Exception {

		_cityRepository.saveAll(cities.stream()
			.map(this::convertCityToModel)
			.collect(Collectors.toList()));
	}

	@GetMapping("/city/{cityId}")
	public Optional<City> getCity(@PathVariable long cityId) throws Exception {

		return _cityRepository.findById(cityId);
	}

	@GetMapping("/city-by-name/{cityName}")
	public Collection<City> getCityByName(@PathVariable String cityName)
		throws Exception {

		return _cityRepository.findByName(cityName);
	}

	@GetMapping("/city-by-name-like/{cityName}")
	public Collection<City> getCityByNameLike(@PathVariable String cityName)
		throws Exception {

		return _cityRepository.findByNameLike("%" + cityName + "%");
	}

	@GetMapping("/city-by-name-ilike/{cityName}")
	public Collection<City> getCityByNameILike(@PathVariable String cityName)
		throws Exception {

		return _cityRepository.findByNameLikeIgnoreCase("%" + cityName + "%");
	}

	private City convertCityToModel(CityContainer.City city) {

		return City.of(
			city.getId(),
			city.getName(),
			city.getCountry(),
			city.getCoord().getLat(),
			city.getCoord().getLon());
	}

	private final CityRepository _cityRepository;

}
