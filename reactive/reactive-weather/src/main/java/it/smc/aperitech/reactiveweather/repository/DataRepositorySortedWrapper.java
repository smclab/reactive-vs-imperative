package it.smc.aperitech.reactiveweather.repository;

import it.smc.aperitech.reactiveweather.model.Data;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * @author Cristian Bianco
 */
@RequiredArgsConstructor(staticName = "of")
public class DataRepositorySortedWrapper implements DataRepository {

	@Override
	public <S extends Data> Mono<S> insert(S entity) {
		return _dataRepository.insert(entity);
	}

	@Override
	public <S extends Data> Flux<S> insert(Iterable<S> entities) {
		return _dataRepository.insert(entities);
	}

	@Override
	public <S extends Data> Flux<S> insert(Publisher<S> entities) {
		return _dataRepository.insert(entities);
	}

	@Override
	public <S extends Data> Mono<S> findOne(Example<S> example) {
		return _dataRepository.findOne(example);
	}

	@Override
	public <S extends Data> Flux<S> findAll(Example<S> example) {
		return _dataRepository.findAll(example);
	}

	@Override
	public <S extends Data> Flux<S> findAll(Example<S> example, Sort sort) {
		return _dataRepository.findAll(example, sort);
	}

	@Override
	public <S extends Data> Mono<Long> count(Example<S> example) {
		return _dataRepository.count(example);
	}

	@Override
	public <S extends Data> Mono<Boolean> exists(Example<S> example) {
		return _dataRepository.exists(example);
	}

	@Override
	public <S extends Data> Mono<S> save(S entity) {
		return _dataRepository.save(entity);
	}

	@Override
	public <S extends Data> Flux<S> saveAll(Iterable<S> entities) {
		return _dataRepository.saveAll(entities);
	}

	@Override
	public <S extends Data> Flux<S> saveAll(Publisher<S> entityStream) {
		return _dataRepository.saveAll(entityStream);
	}

	public Flux<Data> findByCityId(long cityId) {
		return _dataRepository.findByCityId(cityId);
	}

	public Flux<Data> findByCityId(long cityId, Sort sort) {
		return _dataRepository.findByCityId(cityId, sort);
	}

	public Flux<Data> findByCityId(Iterable<Long> cityIds) {
		return _dataRepository.findByCityId(cityIds);
	}

	public Flux<Data> findByDtBetweenOrderByDt(
		LocalDateTime start, LocalDateTime end) {
		return _dataRepository.findByDtBetweenOrderByDt(start, end, _sort);
	}

	public Flux<Data> findByDtGreaterThanEqualOrderByDt(LocalDateTime ldt) {
		return _dataRepository.findByDtGreaterThanEqualOrderByDt(ldt, _sort);
	}

	public Flux<Data> findByDtLessThanEqualOrderByDt(LocalDateTime ldt) {
		return _dataRepository.findByDtLessThanEqualOrderByDt(ldt, _sort);
	}

	public Flux<Data> findByCityIdAndDtBetweenOrderByDt(
		long cityId, LocalDateTime start, LocalDateTime end) {
		return _dataRepository.findByCityIdAndDtBetweenOrderByDt(cityId,
			start, end, _sort);
	}

	public Flux<Data> findByCityIdAndDtGreaterThanEqualOrderByDt(
		long cityId, LocalDateTime ldt) {
		return _dataRepository.findByCityIdAndDtGreaterThanEqualOrderByDt(
			cityId, ldt, _sort);
	}

	public Flux<Data> findByCityIdAndDtLessThanEqualOrderByDt(
		long cityId, LocalDateTime ldt) {
		return _dataRepository.findByCityIdAndDtLessThanEqualOrderByDt(
			cityId, ldt, _sort);
	}

	public Flux<Data> findByDtBetweenOrderByDt(
		LocalDateTime start, LocalDateTime end, Sort sort) {
		return _dataRepository.findByDtBetweenOrderByDt(start, end,
			sort);
	}

	public Flux<Data> findByDtGreaterThanEqualOrderByDt(
		LocalDateTime ldt, Sort sort) {
		return _dataRepository.findByDtGreaterThanEqualOrderByDt(
			ldt,
			sort);
	}

	public Flux<Data> findByDtLessThanEqualOrderByDt(
		LocalDateTime ldt, Sort sort) {
		return _dataRepository.findByDtLessThanEqualOrderByDt(
			ldt,
			sort);
	}

	public Flux<Data> findByCityIdAndDtBetweenOrderByDt(
		long cityId, LocalDateTime start, LocalDateTime end, Sort sort) {
		return _dataRepository.findByCityIdAndDtBetweenOrderByDt(cityId,
			start, end, sort);
	}

	public Flux<Data> findByCityIdAndDtGreaterThanEqualOrderByDt(
		long cityId, LocalDateTime ldt, Sort sort) {
		return _dataRepository.findByCityIdAndDtGreaterThanEqualOrderByDt(
			cityId, ldt, sort);
	}

	public Flux<Data> findByCityIdAndDtLessThanEqualOrderByDt(
		long cityId, LocalDateTime ldt, Sort sort) {
		return _dataRepository.findByCityIdAndDtLessThanEqualOrderByDt(
			cityId, ldt, sort);
	}

	public Flux<Data> findAll(Sort sort) {
		return _dataRepository.findAll(sort);
	}

	public Mono<Data> findById(Long id) {
		return _dataRepository.findById(id);
	}

	public Mono<Data> findById(Publisher<Long> id) {
		return _dataRepository.findById(id);
	}

	public Mono<Boolean> existsById(Long id) {
		return _dataRepository.existsById(id);
	}

	public Mono<Boolean> existsById(Publisher<Long> id) {
		return _dataRepository.existsById(id);
	}

	public Flux<Data> findAll() {
		return _dataRepository.findAll();
	}

	public Flux<Data> findAllById(Iterable<Long> ids) {
		return _dataRepository.findAllById(ids);
	}

	public Flux<Data> findAllById(Publisher<Long> idStream) {
		return _dataRepository.findAllById(idStream);
	}

	public Mono<Long> count() {
		return _dataRepository.count();
	}

	public Mono<Void> deleteById(Long id) {
		return _dataRepository.deleteById(id);
	}

	public Mono<Void> deleteById(Publisher<Long> id) {
		return _dataRepository.deleteById(id);
	}

	public Mono<Void> delete(Data entity) {
		return _dataRepository.delete(entity);
	}

	public Mono<Void> deleteAll(Iterable<? extends Data> entities) {
		return _dataRepository.deleteAll(entities);
	}

	public Mono<Void> deleteAll(Publisher<? extends Data> entityStream) {
		return _dataRepository.deleteAll(entityStream);
	}

	public Mono<Void> deleteAll() {
		return _dataRepository.deleteAll();
	}

	private final DataRepository _dataRepository;
	private final Sort _sort;
	
}
