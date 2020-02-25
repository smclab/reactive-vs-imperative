package it.smc.aperitech.weather.rest;

import it.smc.aperitech.dto.CityContainer;
import it.smc.aperitech.weather.model.Data;
import it.smc.aperitech.weather.model.Weather;
import it.smc.aperitech.weather.repository.DataRepository;
import it.smc.aperitech.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Mauro Celani
 */
@RestController
@RequiredArgsConstructor
public class DataController {

	@PostMapping("/data")
	public void addData(@RequestBody CityContainer cityContainer)
		throws Exception {

		long cityId = cityContainer.getCity().getId();

		_dataRepository.saveAll(cityContainer.getData()
			.stream()
			.map(data -> convertCityToModel(cityId, data))
			.collect(Collectors.toList()));

		_weatherRepository.saveAll(cityContainer.getData()
			.stream()
			.map(CityContainer.Data::getWeather)
			.flatMap(List::stream)
			.distinct()
			.map(Weather::new)
			.collect(Collectors.toList()));
	}

	@PostMapping("/datas")
	public void addDatas(@RequestBody List<CityContainer> cityContainers)
		throws Exception {

		_dataRepository.saveAll(cityContainers.stream()
			.flatMap(cc -> cc.getData()
				.stream()
				.map(data -> convertCityToModel(cc.getCity().getId(), data)))
			.collect(Collectors.toList()));

		_weatherRepository.saveAll(cityContainers
			.stream()
			.flatMap(cc -> cc.getData()
				.stream()
				.map(CityContainer.Data::getWeather)
				.flatMap(List::stream)
			)
			.distinct()
			.map(Weather::new)
			.collect(Collectors.toList()));
	}

	@GetMapping("/datas-by-city-id/{cityId}")
	public List<Data> getDatasByCityId(@PathVariable long cityId)
		throws Exception {

		return _dataRepository.findByCityId(cityId);
	}

	@GetMapping("/weather/{weatherId}")
	public Optional<Weather> getWeather(@PathVariable long weatherId)
		throws Exception {

		return _weatherRepository.findById(weatherId);
	}

	@GetMapping("/datas-between-date")
	public List<Data> findByDtBetweenOrderByDt(
			@RequestParam(required = false) Optional<LocalDateTime> from,
			@RequestParam(required = false) Optional<LocalDateTime> to,
			@RequestParam(required = false) Optional<Long> cityId,
			@RequestParam(required = false) Optional<String> orderByName,
			@RequestParam(required = false) Optional<String> orderByType)
		throws Exception {

		return DateBetweenUtil.findDatasBetweenDate(
			from, to, cityId, orderByName, orderByType)
			.apply(_dataRepository);
	}

	private Data convertCityToModel(long cityId, CityContainer.Data data) {

		return Data.builder()
			.cityId(cityId)
			.dt(data.getDt())
			.temp(data.getMain().getTemp())
			.tempMin(data.getMain().getTemp_min())
			.tempMax(data.getMain().getTemp_max())
			.humidity(data.getMain().getHumidity())
			.weatherId(data.getWeather().get(0).getId())
			.clouds(data.getClouds().getAll())
			.windSpeed(data.getWind().getSpeed())
			.windDeg(data.getWind().getDeg())
			.build();
	}

	private final DataRepository _dataRepository;

	private final WeatherRepository _weatherRepository;

}
