package it.smc.aperitech.reactive.aggregator.proxy;

import it.smc.aperitech.dto.CityContainer;
import it.smc.aperitech.dto.CityContainerModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Collections.singletonList;

/**
 * @author Cristian Bianco
 */
@Service
@RequiredArgsConstructor
public class ReactiveDataProxyImpl implements ReactiveDataProxy {

	@Override
	public Mono<CityContainerModel.Data> addData(
		Mono<CityContainer.Data> cityContainer) {
		return _webClient
			.post()
			.uri("http://reactive-weather/data")
			.body(cityContainer, CityContainer.Data.class)
			.retrieve()
			.bodyToMono(CityContainerModel.Data.class);
	}

	@Override
	public Flux<CityContainerModel.Data> getDatasByCityId(
		long cityId, MediaType...mediaTypes) {
		return _webClient
			.get()
			.uri(ub -> ub
				.scheme("http")
				.host("reactive-weather")
				.path("/datas-by-city/{cityId}")
				.build(cityId))
			.accept(mediaTypes)
			.retrieve()
			.bodyToFlux(CityContainerModel.Data.class);
	}

	@Override
	public Flux<CityContainerModel.Data> getDatasByCityIds(
		Iterable<Long> cityIds) {
		return _webClient
			.get()
			.uri(ub -> ub
				.scheme("http")
				.host("reactive-weather")
				.path("/datas-by-city-ids")
				.queryParam("cityIds", StreamSupport
					.stream(cityIds.spliterator(), false)
					.map(Object::toString)
					.collect(Collectors.joining(",")))
				.build()
			)
			.retrieve()
			.bodyToFlux(CityContainerModel.Data.class);
	}

	@Override
	public Mono<CityContainerModel.Weather> getWeather(long weatherId) {
		return _webClient
			.get()
			.uri(ub -> ub
				.scheme("http")
				.host("reactive-weather")
				.path("/weather/{weatherId}")
				.build(weatherId))
			.retrieve()
			.bodyToMono(CityContainerModel.Weather.class);
	}

	@Override
	public Flux<ServerSentEvent<CityContainerModel.Data>>
		getDataBetweenDateAndCityId(
			String from, String to, long cityId) {
		return _getDataBetweenDate(
			Map.of(
				"from", singletonList(from),
				"to", singletonList(to),
				"cityId", singletonList(String.valueOf(cityId))
			)
		);
	}

	@Override
	public Flux<ServerSentEvent<CityContainerModel.Data>> getDataBetweenDate(
		String from, String to) {
		return _getDataBetweenDate(
			Map.of(
				"from", singletonList(from),
				"to", singletonList(to)
			)
		);
	}

	@Override
	public Flux<ServerSentEvent<CityContainerModel.Data>> getDataFromDate(
		String from) {
		return _getDataBetweenDate(
			Map.of("from", singletonList(from)));
	}

	@Override
	public Flux<ServerSentEvent<CityContainerModel.Data>>
		getDataFromDateAndCityId(String from, long cityId) {

		return _getDataBetweenDate(
			Map.of(
				"from", singletonList(from),
				"cityId", singletonList(String.valueOf(cityId))
			)
		);
	}

	@Override
	public Flux<ServerSentEvent<CityContainerModel.Data>> getDataToDate(
		String to) {
		return _getDataBetweenDate(
			Map.of("to", singletonList(to)));
	}

	@Override
	public Flux<ServerSentEvent<CityContainerModel.Data>>
		getDataToDateAndCityId(String to, long cityId) {

		return _getDataBetweenDate(
			Map.of(
				"to", singletonList(to),
				"cityId", singletonList(String.valueOf(cityId)))
		);
	}

	@Override
	public Flux<ServerSentEvent<CityContainerModel.Data>>
		getDataBetweenDateAndCityId(
			String from, String to, long cityId, String orderByName,
			String orderByType) {
		return _getDataBetweenDate(
			Map.of(
				"from", singletonList(from),
				"to", singletonList(to),
				"cityId", singletonList(String.valueOf(cityId)),
				"orderByName", singletonList(orderByName),
				"orderByType", singletonList(orderByType)
			)
		);
	}

	@Override
	public Flux<ServerSentEvent<CityContainerModel.Data>> getDataBetweenDate(
		String from, String to, String orderByName, String orderByType) {
		return _getDataBetweenDate(
			Map.of(
				"from", singletonList(from),
				"to", singletonList(to),
				"orderByName", singletonList(orderByName),
				"orderByType", singletonList(orderByType)
			)
		);
	}

	@Override
	public Flux<ServerSentEvent<CityContainerModel.Data>> getDataFromDate(
		String from, String orderByName, String orderByType) {
		return _getDataBetweenDate(
			Map.of(
				"from", singletonList(from),
				"orderByName", singletonList(orderByName),
				"orderByType", singletonList(orderByType)
			)
		);
	}

	@Override
	public Flux<ServerSentEvent<CityContainerModel.Data>>
		getDataFromDateAndCityId(
			String from, long cityId, String orderByName, String orderByType) {
		return _getDataBetweenDate(
			Map.of(
				"from", singletonList(from),
				"cityId", singletonList(String.valueOf(cityId)),
				"orderByName", singletonList(orderByName),
				"orderByType", singletonList(orderByType)
			)
		);
	}

	@Override
	public Flux<ServerSentEvent<CityContainerModel.Data>> getDataToDate(
		String to, String orderByName, String orderByType) {
		return _getDataBetweenDate(
			Map.of(
				"to", singletonList(to),
				"orderByName", singletonList(orderByName),
				"orderByType", singletonList(orderByType)
			)
		);
	}

	@Override
	public Flux<ServerSentEvent<CityContainerModel.Data>> getDataToDateAndCityId(
		String to, long cityId, String orderByName, String orderByType) {
		return _getDataBetweenDate(
			Map.of(
				"to", singletonList(to),
				"cityId", singletonList(String.valueOf(cityId)),
				"orderByName", singletonList(orderByName),
				"orderByType", singletonList(orderByType)
			)
		);
	}

	private Flux<ServerSentEvent<CityContainerModel.Data>> _getDataBetweenDate(
		Map<String, List<String>> queryParams) {

		return _webClient
			.get()
			.uri(ub -> ub
				.scheme("http")
				.host("reactive-weather")
				.path("/datas-between-date")
				.queryParams(new LinkedMultiValueMap<>(queryParams))
				.build()
			)
			.accept(MediaType.APPLICATION_STREAM_JSON)
			.retrieve()
			.bodyToFlux(
				new ParameterizedTypeReference<
					ServerSentEvent<CityContainerModel.Data>>() {})
			.onBackpressureBuffer(Integer.MAX_VALUE);
	}

	private final WebClient _webClient;

}
