package it.smc.aperitech.aggregator.fallback;

import feign.hystrix.FallbackFactory;
import it.smc.aperitech.aggregator.proxy.CityProxyImpl;
import it.smc.aperitech.dto.CityContainerModel.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author Mauro Celani
 */
@Slf4j
@Component
public class CityProxyFallbackFactory
	implements FallbackFactory<CityProxyImpl> {

	@Override
	public CityProxyImpl create(Throwable cause) {

		log.error(cause.getMessage(), cause);

		return new CityProxyImpl() {

			@Override
			public CompletableFuture<Void> addCity(City city) {
				return addCities(Collections.emptyList());
			}

			@Override
			public CompletableFuture<Void> addCities(List<City> cities) {
				CompletableFuture<Void> voidCompletableFuture =
					new CompletableFuture<>();

				voidCompletableFuture.completeExceptionally(cause);

				return voidCompletableFuture;

			}

			@Override
			public Optional<City> getCity(long cityId) {
				return Optional.empty();
			}

			@Override
			public Collection<City> getCityByName(String cityName) {
				return Collections.emptyList();
			}

			@Override
			public Collection<City> getCityByNameLike(String cityName) {
				return Collections.emptyList();
			}

			@Override
			public Collection<City> getCityByNameILike(String cityName) {
				return Collections.emptyList();
			}

		};
	}
}
