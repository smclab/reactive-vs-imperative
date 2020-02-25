package it.smc.aperitech.reactiveweather.rest;

import it.smc.aperitech.reactiveweather.DateBetweenUtil;
import it.smc.aperitech.reactiveweather.model.Data;
import it.smc.aperitech.reactiveweather.model.Weather;
import it.smc.aperitech.reactiveweather.repository.DataRepository;
import it.smc.aperitech.reactiveweather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
@RequiredArgsConstructor
public class WeatherController {

	@Bean
	public RouterFunction<ServerResponse> dataRoutes() {
		return route(GET("/data/{id}"), this::findDataById)
				.and(route(GET("/datas"), this::findAllData))
				.and(route(POST("/data"), this::addData)
				.and(route(DELETE("/data/{id}"), this::deleteDataById))
				.and(route(GET("/datas-by-city/{cityId}"),
					this::findDatasByCityId))
				.and(route(GET("/datas-by-city-ids"),
						this::findDatasByCityIds))
				.and(route(GET("/datas-between-date"),
					this::findDatasBetweenDate))
			);
	}

	@Bean
	public RouterFunction<ServerResponse> weatherRoutes() {

		return route(GET("/weather/{id}"), this::findWeatherById)
			.and(route(GET("/weathers"), this::findAllWeathers))
			.and(route(POST("/weather"), this::addWeather)
				.and(route(DELETE("/weather/{id}"),
					this::deleteWeatherById))
			);
	}


	private Mono<ServerResponse> findDatasBetweenDate(ServerRequest request) {

		Flux<Data> response = DateBetweenUtil
			.findDatasBetweenDate(request)
			.apply(_dataRepository);

		if (_isSSERequest(request)) {
			return _toSSEResponse(
				response,
				new ParameterizedTypeReference<ServerSentEvent<Data>>() {},
				d -> String.valueOf(d.getDataId())
			);
		}

		return ok().body(response, Data.class);

	}

	private Mono<ServerResponse> findDataById(ServerRequest req) {
		return ok().body(
			_dataRepository.findById(
				Long.valueOf(req.pathVariable("id"))), Data.class);
	}

	private Mono<ServerResponse> findAllData(ServerRequest req) {

		return _isSSERequest(req)
			? _toSSEResponse(
				_dataRepository.findAll(),
				new ParameterizedTypeReference<ServerSentEvent<Data>>() {},
				d -> String.valueOf(d.getDataId()))
			: ok().body(_dataRepository.findAll(), Data.class);

	}

	private Mono<ServerResponse> addData(ServerRequest req) {
		return req
			.bodyToMono(Data.class)
			.doOnNext(_dataRepository::save)
			.then(ok().build());
	}

	private Mono<ServerResponse> deleteDataById(ServerRequest req) {
		return _dataRepository
			.deleteById(Long.valueOf(req.pathVariable("id")))
			.then(ok().build());
	}

	private Mono<ServerResponse> findDatasByCityId(ServerRequest req) {

		Flux<Data> datas = _dataRepository
			.findByCityId(
				Long.parseLong(
					req.pathVariable("cityId")));

		return _isSSERequest(req)
			? _toSSEResponse(
				datas,
				new ParameterizedTypeReference<ServerSentEvent<Data>>() {},
				data -> String.valueOf(data.getDataId()))
			: ok().body(datas, Data.class);
	}

	private Mono<ServerResponse> findDatasByCityIds(ServerRequest req) {
		return ok()
			.body(
				Flux.concat(
						req.queryParam("cityIds")
							.stream()
							.flatMap(s -> Arrays.stream(s.split(",")))
							.map(Long::valueOf)
							.map(_dataRepository::findByCityId)
							.collect(Collectors.toList()))
					.map(d -> ServerSentEvent.builder(d).build()),
				new ParameterizedTypeReference<ServerSentEvent<Data>>() {});
	}


	private Mono<ServerResponse> findWeatherById(ServerRequest req) {
		return ok().body(
			_weatherRepository.findById(
				Long.valueOf(req.pathVariable("id"))), Weather.class);
	}

	private Mono<ServerResponse> findAllWeathers(ServerRequest req) {
		return _isSSERequest(req)
			? _toSSEResponse(
				_weatherRepository.findAll(),
				new ParameterizedTypeReference<ServerSentEvent<Weather>>() {},
				weather -> String.valueOf(weather.getId()))
			: ok().body(_weatherRepository.findAll(), Weather.class);
	}

	private Mono<ServerResponse> addWeather(ServerRequest req) {
		return req
			.bodyToMono(Weather.class)
			.doOnNext(_weatherRepository::save)
			.then(ok().build());
	}

	private Mono<ServerResponse> deleteWeatherById(ServerRequest req) {
		return _weatherRepository
			.deleteById(Long.valueOf(req.pathVariable("id")))
			.then(ok().build());
	}

	private <T> Mono<ServerResponse> _toSSEResponse(
		Flux<T> flux, ParameterizedTypeReference<ServerSentEvent<T>> ptr,
		Function<T, String> getId, String event) {

		return ok().body(
			flux.map(e -> ServerSentEvent
				.builder(e)
				.id(getId.apply(e))
				.event(event)
				.build()),
			ptr
		);
	}

	private <T> Mono<ServerResponse> _toSSEResponse(
		Flux<T> flux, ParameterizedTypeReference<ServerSentEvent<T>> ptr,
		Function<T, String> getId) {
		return _toSSEResponse(flux, ptr, getId, null);
	}

	private <T> Mono<ServerResponse> _toSSEResponse(
		Flux<T> flux, ParameterizedTypeReference<ServerSentEvent<T>> ptr) {
		return _toSSEResponse(flux, ptr, ignore -> null, null);
	}

	private boolean _isSSERequest(ServerRequest request) {
		return request
			.headers()
			.accept()
			.stream()
			.anyMatch(m ->
				m.equals(MediaType.APPLICATION_OCTET_STREAM)
				|| m.equals(MediaType.APPLICATION_STREAM_JSON));
	}

	private final DataRepository _dataRepository;
	private final WeatherRepository _weatherRepository;

}
