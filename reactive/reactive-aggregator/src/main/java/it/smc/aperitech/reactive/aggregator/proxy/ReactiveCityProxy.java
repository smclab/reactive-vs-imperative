package it.smc.aperitech.reactive.aggregator.proxy;

import it.smc.aperitech.dto.CityContainer;
import it.smc.aperitech.dto.CityContainerModel;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ReactiveCityProxy {

	Mono<CityContainerModel.City> addCity(Mono<CityContainer.City> city);

	Flux<CityContainerModel.City> addCities(Flux<CityContainer.City> cities);

	Mono<CityContainerModel.City> getCity(long cityId);

	Flux<CityContainerModel.City> getCityByName(String name);

	Flux<ServerSentEvent<CityContainerModel.City>> getCityByNameLikeSSE(
		String name);

	Flux<ServerSentEvent<CityContainerModel.City>> getCityByNameStartWithSSE(
		String name);

	Flux<ServerSentEvent<CityContainerModel.City>> getCityByNameEndWithSSE(
		String name);

	Flux<CityContainerModel.City> getCityByNameLike(String name);

	Flux<CityContainerModel.City> getCityByNameStartWith(String name);

	Flux<CityContainerModel.City> getCityByNameEndWith(String name);

	Flux<CityContainerModel.City> getCities(int start, int end);

	default Flux<CityContainerModel.City> addCities(
		List<CityContainer.City> cities) {
		return addCities(Flux.fromIterable(cities));
	}

	default Mono<CityContainerModel.City> addCity(CityContainer.City city) {
		return addCity(Mono.just(city));
	}

	Flux<ServerSentEvent<CityContainerModel.City>> getCitiesStream(
		int start, int end);
}
