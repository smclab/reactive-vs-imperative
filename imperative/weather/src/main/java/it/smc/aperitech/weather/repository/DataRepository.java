package it.smc.aperitech.weather.repository;

import it.smc.aperitech.weather.model.Data;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Mauro Celani
 */
public interface DataRepository extends MongoRepository<Data, Long> {

	List<Data> findByCityId(long cityId);

	List<Data> findByCityId(long cityId, Sort sort);

	List<Data> findByCityId(Iterable<Long> cityIds);

	List<Data> findByDtBetweenOrderByDt(
		LocalDateTime start, LocalDateTime end);

	List<Data> findByDtGreaterThanEqualOrderByDt(LocalDateTime ldt);

	List<Data> findByDtLessThanEqualOrderByDt(LocalDateTime ldt);

	List<Data> findByCityIdAndDtBetweenOrderByDt(
		long cityId, LocalDateTime start, LocalDateTime end);

	List<Data> findByCityIdAndDtGreaterThanEqualOrderByDt(
		long cityId, LocalDateTime ldt);

	List<Data> findByCityIdAndDtLessThanEqualOrderByDt(
		long cityId, LocalDateTime ldt);

	List<Data> findByDtBetweenOrderByDt(
		LocalDateTime start, LocalDateTime end, Sort sort);

	List<Data> findByDtGreaterThanEqualOrderByDt(
		LocalDateTime ldt, Sort sort);

	List<Data> findByDtLessThanEqualOrderByDt(
		LocalDateTime ldt, Sort sort);

	List<Data> findByCityIdAndDtBetweenOrderByDt(
		long cityId, LocalDateTime start, LocalDateTime end, Sort sort);

	List<Data> findByCityIdAndDtGreaterThanEqualOrderByDt(
		long cityId, LocalDateTime ldt, Sort sort);

	List<Data> findByCityIdAndDtLessThanEqualOrderByDt(
		long cityId, LocalDateTime ldt, Sort sort);

}
