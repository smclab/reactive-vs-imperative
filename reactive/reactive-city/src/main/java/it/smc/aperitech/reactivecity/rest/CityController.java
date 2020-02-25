package it.smc.aperitech.reactivecity.rest;

import it.smc.aperitech.reactivecity.model.City;
import it.smc.aperitech.reactivecity.repository.CityRepositoryService;
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

import java.util.Optional;
import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author Cristian Bianco
 */
@Service
@RequiredArgsConstructor
public class CityController {

	@Bean
	public RouterFunction<ServerResponse> cityRoutes() {
		return route(GET("/city/{id}"), this::findById)
			.and(route(GET("/city-by-name/{name}"), this::findByName))
			.and(route(GET(
				"/city-by-name-like/{name}"), this::findByNameLike))
			.and(route(GET(
				"/city-by-name-start-with/{name}"), this::findByNameStartWith))
			.and(route(GET(
				"/city-by-name-end-with/{name}"), this::findByNameEndWith))
			.and(route(POST("/city"), this::addCity))
			.and(route(GET("/cities"), this::streamCities));
	}

	private Mono<ServerResponse> addCity(ServerRequest req) {
		return req
			.bodyToMono(City.class)
			.doOnNext(_cityRepository::save)
			.then(ok().build());
	}

	private Mono<ServerResponse> findById(ServerRequest req) {
		return ok().body(
			_cityRepository.findById(
				Long.valueOf(req.pathVariable("id"))), City.class);
	}

	private Mono<ServerResponse> findByNameLike(ServerRequest request) {
		return _isSSERequest(request)
			? _findByLikeStream(_cityRepository::findByNameLike, request)
			: _findByLike(_cityRepository::findByNameLike, request);
	}

	private Mono<ServerResponse> findByNameStartWith(ServerRequest request) {
		return _isSSERequest(request)
			? _findByLikeStream(_cityRepository::findByNameStartWith, request)
			: _findByLike(_cityRepository::findByNameStartWith, request);
	}

	private Mono<ServerResponse> findByNameEndWith(ServerRequest request) {
		return _isSSERequest(request)
			? _findByLikeStream(_cityRepository::findByNameEndWith, request)
			: _findByLike(_cityRepository::findByNameEndWith, request);
	}

	private Mono<ServerResponse> _findByLike(
		Function<String, Flux<City>> function, ServerRequest request) {

		return ok().body(
			function.apply(request.pathVariable("name")), City.class);

	}

	private Mono<ServerResponse> _findByLikeStream(
		Function<String, Flux<City>> function, ServerRequest request) {

		return _toSSEResponse(
			function.apply(request.pathVariable("name")),
			new ParameterizedTypeReference<ServerSentEvent<City>>() {},
			c -> String.valueOf(c.getId())
		);

	}

	private Mono<ServerResponse> findByName(ServerRequest request) {

		String name = request.pathVariable("name");

		Flux<City> cities = _cityRepository.findByName(name);

		if (_isSSERequest(request)) {

			return _toSSEResponse(
				cities,
				new ParameterizedTypeReference<ServerSentEvent<City>>() {},
				c -> String.valueOf(c.getId()));

		}

		return ok().body(cities, City.class);
	}

	private Mono<ServerResponse> findByNameStream(ServerRequest request) {

		String name = request.pathVariable("name");

		Flux<ServerSentEvent<City>> sse =
			_cityRepository
				.findByName(name)
				.map(city -> ServerSentEvent.builder(city).build());

		return ok().body(
			sse, new ParameterizedTypeReference<ServerSentEvent<City>>() {});
	}

	private Mono<ServerResponse> streamCities(ServerRequest req) {

		Optional<Integer> start =
			req.queryParam("start").map(Integer::parseInt);

		Optional<Integer> end =
			req.queryParam("end").map(Integer::parseInt);

		String orderByName = req.queryParam("orderByName").orElse("name");

		String orderByType = req.queryParam("orderByType").orElse("asc");

		Flux<City> cities =
			_cityRepository
				.findCities(
					start.orElse(0), end.orElse(20), orderByName, orderByType);

		if (_isSSERequest(req)) {
			return _toSSEResponse(
				cities,
				new ParameterizedTypeReference<ServerSentEvent<City>>() {},
				c -> String.valueOf(c.getId()));
		}

		return ok().body(cities, City.class);

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

	private final CityRepositoryService _cityRepository;

}
