package it.smc.aperitech.weather.rest;

import it.smc.aperitech.weather.model.Data;
import it.smc.aperitech.weather.repository.DataRepository;
import it.smc.aperitech.weather.repository.DataRepositorySortedWrapper;
import org.springframework.data.domain.Sort;
import reactor.function.Function4;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Mauro Celani
 */
public class DateBetweenUtil {

	public static Function<DataRepository, List<Data>> findDatasBetweenDate(
		Optional<LocalDateTime> from, Optional<LocalDateTime> to,
		Optional<Long> cityId, Optional<String> orderByName,
		Optional<String> orderByType) {

		return dataRepository -> {

			Sort sort = orderByName
				.flatMap(name -> orderByType
					.or(() -> Optional.of("asc"))
					.map(type -> Sort.by(type.equals("asc")
						? Sort.Order.asc(name)
						: Sort.Order.desc(name)))
				)
				.orElseGet(Sort::unsorted);

			return _getLocalDateTimeOrDefault(from, _FROM)
				.flatMap(f -> _getLocalDateTimeOrDefault(to, _TO)
					.flatMap(t -> cityId
						.map(ci -> Tuples.of(_CITY_ID, ci))
						.or(() -> Optional.of(_DEFAULT_CITY_ID))
						.map(ci -> _functionMap
							.get(f.getT1() + t.getT1() + ci.getT1())
							.apply(DataRepositorySortedWrapper
								.of(dataRepository, sort), ci.getT2(),
								f.getT2(), t.getT2()))))
				.orElseThrow(IllegalStateException::new);
		};
	}

	private static Optional<Tuple2<Integer, LocalDateTime>>
	_getLocalDateTimeOrDefault(Optional<LocalDateTime> ldt, int i) {

		return ldt
			.map(f -> Tuples.of(i, f))
			.or(() -> Optional.of(_DEFAULT_LOCAL_DATE_TIME));
	}

	private static int _ZERO = 		0b0_0_0;

	private static int _FROM =		0b0_0_1;

	private static int _TO = 		0b0_1_0;

	private static int _CITY_ID = 	0b1_0_0;

	private static Tuple2<Integer, LocalDateTime> _DEFAULT_LOCAL_DATE_TIME =
		Tuples.of(_ZERO, LocalDateTime.MIN);

	private static Tuple2<Integer, Long> _DEFAULT_CITY_ID =
		Tuples.of(_ZERO, 0L);

	private static final Map<Integer, Function4<
				DataRepository, Long, LocalDateTime, LocalDateTime, List<Data>>>
		_functionMap = Map.of(
			_ZERO, (a, b, c, d) -> Collections.emptyList(),
			_FROM, (a, b, c, d) -> a.findByDtGreaterThanEqualOrderByDt(c),
			_TO, (a, b, c, d) -> a.findByDtLessThanEqualOrderByDt(d),
			_TO + _FROM, (a, b, c, d) -> a.findByDtBetweenOrderByDt(c, d),
			_CITY_ID, (a, b, c, d) -> Collections.emptyList(),
			_CITY_ID + _FROM, (a, b, c, d) ->
				a.findByCityIdAndDtGreaterThanEqualOrderByDt(b, c),
			_CITY_ID + _TO, (a, b, c, d) ->
				a.findByCityIdAndDtLessThanEqualOrderByDt(b, d),
			_CITY_ID + _TO + _FROM,
			DataRepository::findByCityIdAndDtBetweenOrderByDt
		);

}
