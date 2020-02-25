package it.smc.aperitech.reactive.aggregator.controller;

import it.smc.aperitech.dto.CityContainerModel;
import it.smc.aperitech.reactive.aggregator.proxy.ReactiveCityProxy;
import it.smc.aperitech.reactive.aggregator.proxy.ReactiveDataProxy;
import it.smc.aperitech.reactive.aggregator.util.DateBetweenUtil;
import it.smc.aperitech.reactive.aggregator.util.ReactorUtil;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class AggregatorController {

	@GetMapping("/city-by-name-like-stream/{name}")
	public Flux<ServerSentEvent<CityContainerModel.City>> getCityByNameLikeStream(
		@PathVariable("name") String name,
		@RequestParam("delay") Optional<Long> delay,
		@RequestParam(value = "timeUnit", defaultValue = "MILLISECONDS")
			String timeUnit) {
		return _delay(
			_reactiveCityProxy.getCityByNameLikeSSE(name), delay, timeUnit);
	}

	@GetMapping("/city-by-name-start-with-stream/{name}")
	public Flux<ServerSentEvent<CityContainerModel.City>>
		getCityByNameStartWithStream(
			@PathVariable("name") String name,
			@RequestParam("delay") Optional<Long> delay,
			@RequestParam(value = "timeUnit", defaultValue = "MILLISECONDS")
				String timeUnit) {

		return _delay(
			_reactiveCityProxy.getCityByNameStartWithSSE(name),
			delay, timeUnit);
	}

	@GetMapping("/city-by-name-end-with-stream/{name}")
	public Flux<ServerSentEvent<CityContainerModel.City>>
		getCityByNameEndWithStream(
			@PathVariable("name") String name,
			@RequestParam("delay") Optional<Long> delay,
			@RequestParam(value = "timeUnit", defaultValue = "MILLISECONDS")
				String timeUnit) {

		return _delay(
			_reactiveCityProxy.getCityByNameEndWithSSE(name), delay, timeUnit);
	}

	@GetMapping("/city-by-name-like/{name}")
	public Flux<CityContainerModel.City> getCityByNameLike(
		@PathVariable("name") String name) {
		return _reactiveCityProxy.getCityByNameLike(name);
	}

	@GetMapping("/city-by-name-start-with/{name}")
	public Flux<CityContainerModel.City> getCityByNameStartWith(
		@PathVariable("name") String name) {

		return _reactiveCityProxy.getCityByNameStartWith(name);
	}

	@GetMapping("/city-by-name-end-with/{name}")
	public Flux<CityContainerModel.City> getCityByNameEndWith(
		@PathVariable("name") String name) {

		return _reactiveCityProxy.getCityByNameEndWith(name);
	}

	@GetMapping("/city-name-weather")
	public Flux<CityContainerModel> getCityWeather(
		@RequestParam String cityName) {

		Flux<CityContainerModel.City> cityByName =
			_reactiveCityProxy.getCityByName(cityName);

		return cityByName.concatMap(
			city -> _reactiveDataProxy
				.getDatasByCityId(city.getId())
				.collectList()
				.map(datas -> CityContainerModel.of(city, datas))
		);

	}

	@GetMapping("/city-name-weather-stream")
	public Publisher<ServerSentEvent<CityContainerModel>> getCityWeatherStream(
		@RequestParam String cityName) {

		return getCityWeather(cityName)
			.map(ccm -> ServerSentEvent.builder(ccm).build());

	}

	@GetMapping("/cities-stream")
	public Publisher<ServerSentEvent<CityContainerModel.City>> getCitiesStream(
		@RequestParam(value = "start", defaultValue = "0") int start,
		@RequestParam(value = "end", defaultValue = "20") int end) {
		return _reactiveCityProxy.getCitiesStream(start, end);
	}

	@GetMapping("/cities")
	public Publisher<CityContainerModel.City> getCities(
		@RequestParam(value = "start", defaultValue = "0") int start,
		@RequestParam(value = "end", defaultValue = "20") int end) {
		return _reactiveCityProxy.getCities(start, end);
	}

	@GetMapping(value = "/city-weather-stream")
	public Publisher<ServerSentEvent<CityContainerModel>> cityWeatherStream(
		@RequestParam(value = "start", defaultValue = "0") int start,
		@RequestParam(value = "end", defaultValue = "20") int end,
		@RequestParam("delay") Optional<Long> delay,
		@RequestParam(value = "timeUnit", defaultValue = "MILLISECONDS")
			String timeUnit) {

		Flux<ServerSentEvent<CityContainerModel>> flux =
			_getCityContainerModelFlux(start, end)
				.map(ccm -> ServerSentEvent.builder(ccm).build());

		return _delay(flux, delay, timeUnit);
	}

	@GetMapping("/city-weather-date")
	public Publisher<ServerSentEvent<CityContainerModel.Data>>
		cityWeatherStream(
			@RequestParam("from") Optional<String> from,
			@RequestParam("to") Optional<String> to,
			@RequestParam(value = "orderByName", defaultValue = "dt")
				String orderByName,
			@RequestParam(value = "orderByType", defaultValue = "asc")
				String orderByType,
			@RequestParam("cityId") Optional<Long> cityId,
			@RequestParam("delay") Optional<Long> delay,
			@RequestParam(value = "timeUnit", defaultValue = "MILLISECONDS")
				String timeUnit) {

		Flux<ServerSentEvent<CityContainerModel.Data>> sse =
			DateBetweenUtil
				.findDatasBetweenDate(
					from, to, cityId, orderByName, orderByType)
				.apply(_reactiveDataProxy);

		return _delay(sse, delay, timeUnit);
	}

	@GetMapping(
		value = "/city-weather/{cityId}",
		produces = {
			MediaType.APPLICATION_JSON_VALUE
		}
	)
	public Publisher<CityContainerModel> cityWeather(
		@PathVariable("cityId") int cityId) {

		return Mono.zip(
			_reactiveCityProxy.getCity(cityId),
			_reactiveDataProxy.getDatasByCityId(cityId).collectList(),
			CityContainerModel::of);

	}

	@GetMapping(
		value = "/city-weather",
		produces = {
			MediaType.APPLICATION_JSON_VALUE
		}
	)
	public Publisher<List<CityContainerModel>> cityWeather(
		@RequestParam(value = "start", defaultValue = "0") int start,
		@RequestParam(value = "end", defaultValue = "20") int end) {

		return _getCityContainerModelFlux(start, end).collectList();
	}

	private <T> Flux<T> _delay(
		Flux<T> flux, Optional<Long> delay, String timeUnit) {

		return delay
			.map(d -> ReactorUtil.delay(flux, TimeUnit.valueOf(timeUnit), d))
			.orElse(flux);
	}

	private Flux<CityContainerModel> _getCityContainerModelFlux(
		int start, int end) {
		return _reactiveCityProxy.getCities(start, end)
			.concatMap(this::_getCityContainerModelMono);
	}

	private Mono<CityContainerModel> _getCityContainerModelMono(
		CityContainerModel.City city) {
		return _reactiveDataProxy.getDatasByCityId(city.getId())
			.collectList()
			.map(list -> CityContainerModel.of(city, list));
	}

	private final ReactiveCityProxy _reactiveCityProxy;

	private final ReactiveDataProxy _reactiveDataProxy;

}
