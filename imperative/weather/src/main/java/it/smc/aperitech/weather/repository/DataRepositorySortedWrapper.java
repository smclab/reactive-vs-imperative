package it.smc.aperitech.weather.repository;

import it.smc.aperitech.weather.model.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Cristian Bianco
 */
@RequiredArgsConstructor(staticName = "of")
public class DataRepositorySortedWrapper implements DataRepository {

	public List<Data> findByCityId(long cityId) {
		return _dataRepository.findByCityId(cityId);
	}

	public List<Data> findByCityId(long cityId, Sort sort) {
		return _dataRepository.findByCityId(cityId, sort);
	}

	public List<Data> findByCityId(Iterable<Long> cityIds) {
		return _dataRepository.findByCityId(cityIds);
	}

	public List<Data> findByDtBetweenOrderByDt(
		LocalDateTime start, LocalDateTime end) {
		return _dataRepository.findByDtBetweenOrderByDt(start, end, _sort);
	}

	public List<Data> findByDtGreaterThanEqualOrderByDt(LocalDateTime ldt) {
		return _dataRepository.findByDtGreaterThanEqualOrderByDt(ldt, _sort);
	}

	public List<Data> findByDtLessThanEqualOrderByDt(LocalDateTime ldt) {
		return _dataRepository.findByDtLessThanEqualOrderByDt(ldt, _sort);
	}

	public List<Data> findByCityIdAndDtBetweenOrderByDt(
		long cityId, LocalDateTime start, LocalDateTime end) {
		return _dataRepository.findByCityIdAndDtBetweenOrderByDt(cityId,
			start, end, _sort);
	}

	public List<Data> findByCityIdAndDtGreaterThanEqualOrderByDt(
		long cityId, LocalDateTime ldt) {
		return _dataRepository.findByCityIdAndDtGreaterThanEqualOrderByDt(
			cityId, ldt, _sort);
	}

	public List<Data> findByCityIdAndDtLessThanEqualOrderByDt(
		long cityId, LocalDateTime ldt) {
		return _dataRepository.findByCityIdAndDtLessThanEqualOrderByDt(
			cityId, ldt, _sort);
	}

	public List<Data> findByDtBetweenOrderByDt(
		LocalDateTime start, LocalDateTime end, Sort sort) {
		return _dataRepository.findByDtBetweenOrderByDt(start, end, sort);
	}

	public List<Data> findByDtGreaterThanEqualOrderByDt(
		LocalDateTime ldt, Sort sort) {
		return _dataRepository.findByDtGreaterThanEqualOrderByDt(
			ldt, sort);
	}

	public List<Data> findByDtLessThanEqualOrderByDt(
		LocalDateTime ldt, Sort sort) {
		return _dataRepository.findByDtLessThanEqualOrderByDt(ldt, sort);
	}

	public List<Data> findByCityIdAndDtBetweenOrderByDt(
		long cityId, LocalDateTime start, LocalDateTime end, Sort sort) {
		return _dataRepository.findByCityIdAndDtBetweenOrderByDt(cityId,
			start, end, sort);
	}

	public List<Data> findByCityIdAndDtGreaterThanEqualOrderByDt(
		long cityId, LocalDateTime ldt, Sort sort) {
		return _dataRepository.findByCityIdAndDtGreaterThanEqualOrderByDt(
			cityId, ldt, sort);
	}

	public List<Data> findByCityIdAndDtLessThanEqualOrderByDt(
		long cityId, LocalDateTime ldt, Sort sort) {
		return _dataRepository.findByCityIdAndDtLessThanEqualOrderByDt(
			cityId, ldt, sort);
	}

	public <S extends Data> List<S> saveAll(Iterable<S> entities) {
		return _dataRepository.saveAll(entities);
	}

	public List<Data> findAll() {
		return _dataRepository.findAll();
	}

	public List<Data> findAll(Sort sort) {
		return _dataRepository.findAll(sort);
	}

	public <S extends Data> S insert(S entity) {
		return _dataRepository.insert(entity);
	}

	public <S extends Data> List<S> insert(Iterable<S> entities) {
		return _dataRepository.insert(entities);
	}

	public <S extends Data> List<S> findAll(Example<S> example) {
		return _dataRepository.findAll(example);
	}

	public <S extends Data> List<S> findAll(Example<S> example, Sort sort) {
		return _dataRepository.findAll(example, sort);
	}

	public Page<Data> findAll(Pageable pageable) {
		return _dataRepository.findAll(pageable);
	}

	public <S extends Data> S save(S entity) {
		return _dataRepository.save(entity);
	}

	public Optional<Data> findById(Long id) {
		return _dataRepository.findById(id);
	}

	public boolean existsById(Long id) {
		return _dataRepository.existsById(id);
	}

	public Iterable<Data> findAllById(Iterable<Long> ids) {
		return _dataRepository.findAllById(ids);
	}

	public long count() {
		return _dataRepository.count();
	}

	public void deleteById(Long id) {
		_dataRepository.deleteById(id);
	}

	public void delete(Data entity) {
		_dataRepository.delete(entity);
	}

	public void deleteAll(Iterable<? extends Data> entities) {
		_dataRepository.deleteAll(entities);
	}

	public void deleteAll() {
		_dataRepository.deleteAll();
	}

	public <S extends Data> Optional<S> findOne(Example<S> example) {
		return _dataRepository.findOne(example);
	}

	public <S extends Data> Page<S> findAll(
		Example<S> example, Pageable pageable) {
		return _dataRepository.findAll(example, pageable);
	}

	public <S extends Data> long count(Example<S> example) {
		return _dataRepository.count(example);
	}

	public <S extends Data> boolean exists(Example<S> example) {
		return _dataRepository.exists(example);
	}

	private final DataRepository _dataRepository;
	private final Sort _sort;

}
