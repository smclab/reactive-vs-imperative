package it.smc.aperitech.reactiveweather;

import io.vavr.Function4;
import it.smc.aperitech.reactiveweather.model.Data;
import it.smc.aperitech.reactiveweather.repository.DataRepository;
import it.smc.aperitech.reactiveweather.repository.DataRepositorySortedWrapper;
import org.springframework.data.domain.Sort;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class DateBetweenUtil {

	public static Function<DataRepository, Flux<Data>>
		findDatasBetweenDate(ServerRequest request) {

		return dataRepository ->
			Flux.defer(() -> {

				Sort sort = request
					.queryParam("orderByName")
					.flatMap(name -> request
						.queryParam("orderByType")
						.or(() -> Optional.of("asc"))
						.map(type -> Sort.by(type.equals("asc")
							? Sort.Order.asc(name)
							: Sort.Order.desc(name)))
						)
					.orElseGet(Sort::unsorted);

				return _getLocalDateTimeOrDefault(request, _FROM_NAME, _FROM)
					.flatMap(f ->
						_getLocalDateTimeOrDefault(request, _TO_NAME, _TO)
							.flatMap(t -> request
								.queryParam(_CITY_NAME)
								.map(Long::parseLong)
								.map(ci -> Tuples.of(_CITY_ID, ci))
								.or(() -> Optional.of(_DEFAULT_CITY_ID))
								.map(ci -> _functionMap
									.get(f.getT1() + t.getT1() + ci.getT1())
									.apply(DataRepositorySortedWrapper
										.of(dataRepository, sort))
									.apply(ci.getT2(), f.getT2(), t.getT2()))))
					.orElseThrow(IllegalStateException::new);
			});

	}

	private static Optional<Tuple2<Integer, LocalDateTime>>
		_getLocalDateTimeOrDefault(ServerRequest request, String from, int i) {

		return _getLocalDateTime(request, from)
			.map(f -> Tuples.of(i, f))
			.or(() -> Optional.of(_DEFAULT_LOCAL_DATE_TIME));
	}

	private static Optional<LocalDateTime> _getLocalDateTime(
		ServerRequest request, String from) {

		return request
			.queryParam(from)
			.map(LocalDateTime::parse);
	}

	private static final String _FROM_NAME = "from";
	private static final String _TO_NAME = "to";
	private static final String _CITY_NAME = "cityId";

	private static int _ZERO = 		0b0_0_0;

	private static int _FROM =		0b0_0_1;

	private static int _TO = 		0b0_1_0;

	private static int _CITY_ID = 	0b1_0_0;

	private static Tuple2<Integer, LocalDateTime> _DEFAULT_LOCAL_DATE_TIME =
		Tuples.of(_ZERO, LocalDateTime.MIN);

	private static Tuple2<Integer, Long> _DEFAULT_CITY_ID =
		Tuples.of(_ZERO, 0L);

	private static final Map<Integer, Function4<
		DataRepository, Long, LocalDateTime, LocalDateTime, Flux<Data>>>
		_functionMap =
		Map.of(
			_ZERO, (a, b, c, d) -> Flux.empty(),
			_FROM, (a, b, c, d) -> a.findByDtGreaterThanEqualOrderByDt(c),
			_TO, (a, b, c, d) -> a.findByDtLessThanEqualOrderByDt(d),
			_TO + _FROM, (a, b, c, d) -> a.findByDtBetweenOrderByDt(c, d),
			_CITY_ID, (a, b, c, d) -> Flux.empty(),
			_CITY_ID + _FROM, (a, b, c, d) ->
				a.findByCityIdAndDtGreaterThanEqualOrderByDt(b, c),
			_CITY_ID + _TO, (a, b, c, d) ->
				a.findByCityIdAndDtLessThanEqualOrderByDt(b, d),
			_CITY_ID + _TO + _FROM, DataRepository::findByCityIdAndDtBetweenOrderByDt
		);

}
