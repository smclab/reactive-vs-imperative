package it.smc.aperitech.aggregator.fallback;

import feign.hystrix.FallbackFactory;
import it.smc.aperitech.aggregator.proxy.WeatherProxyImpl;
import it.smc.aperitech.dto.CityContainerModel;
import it.smc.aperitech.dto.CityContainerModel.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author Mauro Celani
 */
@Slf4j
@Component
public class WeatherProxyFallbackFactory
	implements FallbackFactory<WeatherProxyImpl> {

	@Override
	public WeatherProxyImpl create(Throwable cause) {

		log.error(cause.getMessage(), cause);

		return new WeatherProxyImpl() {


			@Override
			public CompletableFuture<Void> addData(
				CityContainerModel cityContainer) {

				return addDatas(Collections.emptyList());
			}

			@Override
			public CompletableFuture<Void> addDatas(
				List<CityContainerModel> cityContainers) {

				CompletableFuture<Void> voidCompletableFuture =
					new CompletableFuture<>();

				voidCompletableFuture.completeExceptionally(cause);

				return voidCompletableFuture;
			}

			@Override
			public List<CityContainerModel.Data> getDatasByCityId(long cityId) {

				return Collections.emptyList();
			}

			@Override
			public Optional<Weather> getWeather(long weatherId) {

				return Optional.empty();
			}

			@Override
			public List<CityContainerModel.Data> findByDtBetweenOrderByFromDt(
					String from)
				throws Exception {

				return Collections.emptyList();
			}

			@Override
			public List<CityContainerModel.Data> findByDtBetweenOrderByToDt(
					String to)
				throws Exception {

				return Collections.emptyList();
			}

			@Override
			public List<CityContainerModel.Data> findByDtBetweenOrderByFromDt(
					String from, Long cityId)
				throws Exception {

				return Collections.emptyList();
			}

			@Override
			public List<CityContainerModel.Data> findByDtBetweenOrderByToDt(
					String to, Long cityId)
				throws Exception {

				return Collections.emptyList();
			}

			@Override
			public List<CityContainerModel.Data> findByDtBetweenOrderByFromDt(
					String from, String orderByName, String orderByType)
				throws Exception {

				return Collections.emptyList();
			}

			@Override
			public List<CityContainerModel.Data> findByDtBetweenOrderByToDt(
					String to, String orderByName, String orderByType)
				throws Exception {

				return Collections.emptyList();
			}

			@Override
			public List<CityContainerModel.Data> findByDtBetweenOrderByFromDt(
					String from, Long cityId, String orderByName,
					String orderByType)
				throws Exception {

				return Collections.emptyList();
			}

			@Override
			public List<CityContainerModel.Data> findByDtBetweenOrderByToDt(
					String to, Long cityId, String orderByName,
					String orderByType)
				throws Exception {

				return Collections.emptyList();
			}

			@Override
			public List<CityContainerModel.Data> findByDtBetweenOrderByDt(
					String from, String to, Long cityId)
				throws Exception {

				return Collections.emptyList();
			}

			@Override
			public List<CityContainerModel.Data> findByDtBetweenOrderByDt(
					String from, String to, Long cityId, String orderByName,
					String orderByType)
				throws Exception {

				return Collections.emptyList();
			}

		};
	}
}
