package it.smc.aperitech.reactive.aggregator.util;

import io.vavr.Function2;
import io.vavr.Function4;
import it.smc.aperitech.dto.CityContainerModel;
import it.smc.aperitech.reactive.aggregator.proxy.ReactiveDataProxy;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class DateBetweenUtil {

	public static Function<
		ReactiveDataProxy, Flux<ServerSentEvent<CityContainerModel.Data>>>
		findDatasBetweenDate(
			Optional<String> from, Optional<String> to,
			Optional<Long> cityId, String name, String type) {

		return reactiveDataProxy ->
				_getLocalDateTimeOrDefault(from, _FROM)
					.flatMap(f ->
						_getLocalDateTimeOrDefault(to, _TO)
							.flatMap(t -> cityId
								.map(ci -> Tuples.of(_CITY_ID, ci))
								.or(() -> Optional.of(_DEFAULT_CITY_ID))
								.map(ci -> _functionMap
									.get(f.getT1() + t.getT1() + ci.getT1())
									.apply(reactiveDataProxy)
									.apply(ci.getT2(), f.getT2(), t.getT2())
									.apply(name, type)
								)
							)
					)
					.orElseThrow(IllegalStateException::new);

	}

	private static Optional<Tuple2<Integer, String>>
		_getLocalDateTimeOrDefault(
			Optional<String> fromOrTo, int i) {

		return fromOrTo
			.map(f -> Tuples.of(i, f))
			.or(() -> Optional.of(_DEFAULT_LOCAL_DATE_TIME));
	}

	private static int _ZERO = 		0b0_0_0;

	private static int _FROM =		0b0_0_1;

	private static int _TO = 		0b0_1_0;

	private static int _CITY_ID = 	0b1_0_0;

	private static Tuple2<Integer, String> _DEFAULT_LOCAL_DATE_TIME =
		Tuples.of(_ZERO, "");

	private static Tuple2<Integer, Long> _DEFAULT_CITY_ID =
		Tuples.of(_ZERO, 0L);

	private static final Map<Integer, Function4<
				ReactiveDataProxy, Long, String, String,
					Function2<String, String,
						Flux<ServerSentEvent<CityContainerModel.Data>>>>>
		_functionMap =
		Map.of(
			_ZERO, (a, b, c, d) -> (name, type) -> Flux.empty(),
			_FROM, (a, b, c, d) -> (name, type) ->
				a.getDataFromDate(c, name, type),
			_TO, (a, b, c, d) -> (name, type) ->
				a.getDataToDate(d, name, type),
			_TO + _FROM, (a, b, c, d) -> (name, type) ->
				a.getDataBetweenDate(c, d, name, type),
			_CITY_ID, (a, b, c, d) -> (name, type) ->  Flux.empty(),
			_CITY_ID + _FROM, (a, b, c, d) -> (name, type) ->
				a.getDataFromDateAndCityId(c, b, name, type),
			_CITY_ID + _TO, (a, b, c, d) -> (name, type) ->
				a.getDataToDateAndCityId(d, b, name, type),
			_CITY_ID + _TO + _FROM, (a, b, c, d) -> (name, type) ->
				a.getDataBetweenDateAndCityId(c, d, b, name, type)
		);

}
