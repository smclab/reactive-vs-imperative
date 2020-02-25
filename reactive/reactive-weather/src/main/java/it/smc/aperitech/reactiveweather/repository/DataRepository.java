package it.smc.aperitech.reactiveweather.repository;

import it.smc.aperitech.reactiveweather.model.Data;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

/**
 * @author Cristian Bianco
 */
public interface DataRepository extends ReactiveMongoRepository<Data, Long> {

	Flux<Data> findByCityId(long cityId);

	Flux<Data> findByCityId(long cityId, Sort sort);

	Flux<Data> findByCityId(Iterable<Long> cityIds);

	Flux<Data> findByDtBetweenOrderByDt(
		LocalDateTime start, LocalDateTime end);

	Flux<Data> findByDtGreaterThanEqualOrderByDt(LocalDateTime ldt);

	Flux<Data> findByDtLessThanEqualOrderByDt(LocalDateTime ldt);

	Flux<Data> findByCityIdAndDtBetweenOrderByDt(
		long cityId, LocalDateTime start, LocalDateTime end);

	Flux<Data> findByCityIdAndDtGreaterThanEqualOrderByDt(
		long cityId, LocalDateTime ldt);

	Flux<Data> findByCityIdAndDtLessThanEqualOrderByDt(
		long cityId, LocalDateTime ldt);

	Flux<Data> findByDtBetweenOrderByDt(
		LocalDateTime start, LocalDateTime end, Sort sort);

	Flux<Data> findByDtGreaterThanEqualOrderByDt(
		LocalDateTime ldt, Sort sort);

	Flux<Data> findByDtLessThanEqualOrderByDt(
		LocalDateTime ldt, Sort sort);

	Flux<Data> findByCityIdAndDtBetweenOrderByDt(
		long cityId, LocalDateTime start, LocalDateTime end, Sort sort);

	Flux<Data> findByCityIdAndDtGreaterThanEqualOrderByDt(
		long cityId, LocalDateTime ldt, Sort sort);

	Flux<Data> findByCityIdAndDtLessThanEqualOrderByDt(
		long cityId, LocalDateTime ldt, Sort sort);

}
