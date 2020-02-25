package it.smc.aperitech.reactive.aggregator.proxy;

import it.smc.aperitech.dto.CityContainer;
import it.smc.aperitech.dto.CityContainerModel;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Mauro Celani
 */
public interface ReactiveDataProxy {

	Mono<CityContainerModel.Data> addData(
		Mono<CityContainer.Data> cityContainer);

	Flux<CityContainerModel.Data> getDatasByCityId(
		long cityId, MediaType...mediaTypes);

	Flux<CityContainerModel.Data> getDatasByCityIds(
		Iterable<Long> cityIds);

	Mono<CityContainerModel.Weather> getWeather(long weatherId);

	default Flux<CityContainerModel.Data> getDatasByCityId(long cityId) {
		return getDatasByCityId(cityId, MediaType.APPLICATION_JSON);
	}

	default Mono<CityContainerModel.Data> addData(
		CityContainer.Data cityContainer) {
		return addData(Mono.just(cityContainer));
	}

	Flux<ServerSentEvent<CityContainerModel.Data>> getDataBetweenDateAndCityId(
		String from, String to, long cityId);

	Flux<ServerSentEvent<CityContainerModel.Data>> getDataBetweenDate(
		String from, String to);

	Flux<ServerSentEvent<CityContainerModel.Data>> getDataFromDate(String from);

	Flux<ServerSentEvent<CityContainerModel.Data>> getDataFromDateAndCityId(
		String from, long cityId);

	Flux<ServerSentEvent<CityContainerModel.Data>> getDataToDate(String to);

	Flux<ServerSentEvent<CityContainerModel.Data>> getDataToDateAndCityId(
		String to, long cityId);

	Flux<ServerSentEvent<CityContainerModel.Data>> getDataBetweenDateAndCityId(
		String from, String to, long cityId, String orderByName,
		String orderByType);

	Flux<ServerSentEvent<CityContainerModel.Data>> getDataBetweenDate(
		String from, String to, String orderByName, String orderByType);

	Flux<ServerSentEvent<CityContainerModel.Data>> getDataFromDate(
		String from, String orderByName, String orderByType);

	Flux<ServerSentEvent<CityContainerModel.Data>> getDataFromDateAndCityId(
		String from, long cityId, String orderByName, String orderByType);

	Flux<ServerSentEvent<CityContainerModel.Data>> getDataToDate(
		String to, String orderByName, String orderByType);

	Flux<ServerSentEvent<CityContainerModel.Data>> getDataToDateAndCityId(
		String to, long cityId, String orderByName, String orderByType);

}
