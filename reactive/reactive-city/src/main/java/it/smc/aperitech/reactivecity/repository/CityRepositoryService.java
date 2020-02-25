package it.smc.aperitech.reactivecity.repository;

import it.smc.aperitech.reactivecity.model.City;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CityRepositoryService implements CityRepository {

	public Flux<City> findByName(String name) {
		return _databaseClient
			.select()
			.from(City.class)
			.matching(Criteria.where("name").is(name))
			.fetch()
			.all();
	}

	public Flux<City> findByNameLike(String name) {
		return _findByLike("%" + name + "%");
	}

	public Flux<City> findByNameStartWith(String name) {
		return _findByLike(name + "%");
	}

	public Flux<City> findByNameEndWith(String name) {
		return _findByLike("%" + name);
	}

	public Flux<City> findCities(int start, int end) {
		return findCities(PageRequest.of(start, end));
	}

	public Flux<City> findCities(int start, int end, Sort sort) {
		return findCities(PageRequest.of(start, end, sort));
	}

	public Flux<City> findCities(
		int start, int end, String orderByName, String orderByType) {
		return findCities(
			PageRequest.of(
				start,
				end,
				Sort.by(
					orderByType.equalsIgnoreCase("asc")
						? Sort.Order.asc(orderByName)
						: Sort.Order.desc(orderByName)
				)
			)
		);
	}

	public Flux<City> findCities(PageRequest pageRequest) {
		Objects.requireNonNull(pageRequest, "pageRequest is null");
		return _databaseClient
			.select()
			.from(City.class)
			.page(pageRequest)
			.fetch()
			.all();
	}

	public <S extends City> Mono<S> save(S entity) {
		return _cityRepository.save(entity);
	}

	public <S extends City> Flux<S> saveAll(Iterable<S> entities) {
		return _cityRepository.saveAll(entities);
	}

	public <S extends City> Flux<S> saveAll(Publisher<S> entityStream) {
		return _cityRepository.saveAll(entityStream);
	}

	public Mono<City> findById(Long id) {
		return _cityRepository.findById(id);
	}

	public Mono<City> findById(Publisher<Long> id) {
		return _cityRepository.findById(id);
	}

	public Mono<Boolean> existsById(Long id) {
		return _cityRepository.existsById(id);
	}

	public Mono<Boolean> existsById(Publisher<Long> id) {
		return _cityRepository.existsById(id);
	}

	public Flux<City> findAll() {
		return _cityRepository.findAll();
	}

	public Flux<City> findAllById(Iterable<Long> ids) {
		return _cityRepository.findAllById(ids);
	}

	public Flux<City> findAllById(Publisher<Long> idStream) {
		return _cityRepository.findAllById(idStream);
	}

	public Mono<Long> count() {
		return _cityRepository.count();
	}

	public Mono<Void> deleteById(Long id) {
		return _cityRepository.deleteById(id);
	}

	public Mono<Void> deleteById(Publisher<Long> id) {
		return _cityRepository.deleteById(id);
	}

	public Mono<Void> delete(City entity) {
		return _cityRepository.delete(entity);
	}

	public Mono<Void> deleteAll(Iterable<? extends City> entities) {
		return _cityRepository.deleteAll(entities);
	}

	public Mono<Void> deleteAll(Publisher<? extends City> entityStream) {
		return _cityRepository.deleteAll(entityStream);
	}

	public Mono<Void> deleteAll() {
		return _cityRepository.deleteAll();
	}

	private Flux<City> _findByLike(String keyword) {
		return _databaseClient
			.execute("SELECT * FROM CITY WHERE lower(name) LIKE :name")
			.bind("name", keyword.toLowerCase())
			.as(City.class)
			.fetch()
			.all();
	}

	private final CityRepository _cityRepository;
	private final DatabaseClient _databaseClient;
	
}
