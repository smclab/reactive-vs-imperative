package it.smc.aperitech.reactive.aggregator.proxy;

import it.smc.aperitech.dto.CityContainer;
import it.smc.aperitech.dto.CityContainerModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.concurrent.Queues;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveCityProxyImpl implements ReactiveCityProxy {

	@Override
	public Mono<CityContainerModel.City> addCity(Mono<CityContainer.City> city) {
		return _webClient
			.post()
			.uri("http://reactive-city/city")
			.body(city, CityContainer.City.class)
			.retrieve()
			.bodyToMono(CityContainerModel.City.class);
	}

	@Override
	public Flux<CityContainerModel.City> addCities(
		Flux<CityContainer.City> cities) {
		return _webClient
			.post()
			.uri("http://reactive-city/cities")
			.body(cities, CityContainer.City.class)
			.retrieve()
			.bodyToFlux(CityContainerModel.City.class);
	}

	@Override
	public Mono<CityContainerModel.City> getCity(long cityId) {
		return _webClient
			.get()
			.uri("http://reactive-city/city/" + cityId)
			.retrieve()
			.bodyToMono(CityContainerModel.City.class);
	}

	@Override
	public Flux<CityContainerModel.City> getCityByName(String name) {
		return _getResponseCityByNameLike(
			"/city-by-name/{name}", name, MediaType.APPLICATION_JSON)
			.bodyToFlux(CityContainerModel.City.class);
	}

	@Override
	public Flux<ServerSentEvent<CityContainerModel.City>> getCityByNameLikeSSE(
		String name) {
		return _getCityByNameLikeSSE("/city-by-name-like/{name}", name);

	}

	@Override
	public Flux<ServerSentEvent<CityContainerModel.City>>
		getCityByNameStartWithSSE(String name) {

		return _getCityByNameLikeSSE("/city-by-name-start-with/{name}", name);
	}

	@Override
	public Flux<ServerSentEvent<CityContainerModel.City>> getCityByNameEndWithSSE(
		String name) {
		return _getCityByNameLikeSSE("/city-by-name-end-with/{name}", name);
	}

	@Override
	public Flux<CityContainerModel.City> getCityByNameLike(String name) {
		return _getCityByNameLike("/city-by-name-like/{name}", name);
	}

	@Override
	public Flux<CityContainerModel.City> getCityByNameStartWith(String name) {
		return _getCityByNameLike("/city-by-name-start-with/{name}", name);
	}

	@Override
	public Flux<CityContainerModel.City> getCityByNameEndWith(String name) {
		return _getCityByNameLike("/city-by-name-end-with/{name}", name);
	}

	private Flux<ServerSentEvent<CityContainerModel.City>>
		_getCityByNameLikeSSE(String path, String name) {

		return _getResponseCityByNameLike(
			path, name, MediaType.APPLICATION_STREAM_JSON)
			.bodyToFlux(
				new ParameterizedTypeReference<
					ServerSentEvent<CityContainerModel.City>>() {})
			.onBackpressureBuffer(Integer.MAX_VALUE);
	}

	private Flux<CityContainerModel.City> _getCityByNameLike(
		String path, String name) {

		return _getResponseCityByNameLike(
			path, name, MediaType.APPLICATION_JSON)
			.bodyToFlux(CityContainerModel.City.class);
	}

	private WebClient.ResponseSpec _getResponseCityByNameLike(
		String path, String name, MediaType applicationStreamJson) {
		return _webClient
			.get()
			.uri(uriBuilder -> uriBuilder
				.scheme("http")
				.host("reactive-city")
				.path(path)
				.build(name))
			.accept(applicationStreamJson)
			.retrieve();
	}

	@Override
	public Flux<CityContainerModel.City> getCities(
		int start, int end) {

		return _webClient
			.get()
			.uri(uriBuilder -> uriBuilder
				.scheme("http")
				.host("reactive-city")
				.path("/cities")
				.queryParam("start", start)
				.queryParam("end", end).build())
			.retrieve()
			.bodyToFlux(CityContainerModel.City.class);
	}

	@Override
	public Flux<ServerSentEvent<CityContainerModel.City>> getCitiesStream(
		int start, int end) {

		return _webClient
			.get()
			.uri(uriBuilder -> uriBuilder
				.scheme("http")
				.host("reactive-city")
				.path("/cities")
				.queryParam("start", start)
				.queryParam("end", end).build())
			.accept(MediaType.APPLICATION_STREAM_JSON)
			.retrieve()
			.bodyToFlux(
				new ParameterizedTypeReference<
					ServerSentEvent<CityContainerModel.City>>() {})
			.onBackpressureBuffer(Integer.MAX_VALUE);
	}

	private final WebClient _webClient;

}
