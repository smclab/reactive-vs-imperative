package it.smc.aperitech.proxy;

import it.smc.aperitech.dto.CityContainerModel;
import it.smc.aperitech.dto.CityContainerModel.Data;
import it.smc.aperitech.dto.CityContainerModel.Weather;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author Mauro Celani
 */
public interface WeatherProxy {

	@POST
	@Path("/data")
	@Consumes("application/json")
	CompletableFuture<Void> addData(CityContainerModel cityContainer);

	@POST
	@Path("/datas")
	@Consumes("application/json")
	CompletableFuture<Void> addDatas(List<CityContainerModel> cityContainers);

	@GET
	@Path("/datas-by-city-id/{cityId}")
	List<CityContainerModel.Data> getDatasByCityId(
		@PathParam("cityId") long cityId);

	@GET
	@Path("/weather/{weatherId}")
	Optional<Weather> getWeather(
		@PathParam("weatherId") long weatherId);

	@GET
	@Path("/datas-between-date")
	List<Data> findByDtBetweenOrderByFromDt(
			@QueryParam("from") String from)
		throws Exception;

	@GET
	@Path("/datas-between-date")
	List<Data> findByDtBetweenOrderByToDt(
			@QueryParam("to") String to)
		throws Exception;

	@GET
	@Path("/datas-between-date")
	List<Data> findByDtBetweenOrderByFromDt(
			@QueryParam("from") String from,
			@QueryParam("cityId") Long cityId)
		throws Exception;

	@GET
	@Path("/datas-between-date")
	List<Data> findByDtBetweenOrderByToDt(
			@QueryParam("to") String to,
			@QueryParam("cityId") Long cityId)
		throws Exception;


	@GET
	@Path("/datas-between-date")
	List<Data> findByDtBetweenOrderByFromDt(
			@QueryParam("from") String from,
			@QueryParam("orderByName") String orderByName,
			@QueryParam("orderByType") String orderByType)
		throws Exception;

	@GET
	@Path("/datas-between-date")
	List<Data> findByDtBetweenOrderByToDt(
			@QueryParam("to") String to,
			@QueryParam("orderByName") String orderByName,
			@QueryParam("orderByType") String orderByType)
		throws Exception;

	@GET
	@Path("/datas-between-date")
	List<Data> findByDtBetweenOrderByFromDt(
			@QueryParam("from") String from,
			@QueryParam("cityId") Long cityId,
			@QueryParam("orderByName") String orderByName,
			@QueryParam("orderByType") String orderByType)
		throws Exception;

	@GET
	@Path("/datas-between-date")
	List<Data> findByDtBetweenOrderByToDt(
			@QueryParam("to") String to,
			@QueryParam("cityId") Long cityId,
			@QueryParam("orderByName") String orderByName,
			@QueryParam("orderByType") String orderByType)
		throws Exception;

	@GET
	@Path("/datas-between-date")
	List<Data> findByDtBetweenOrderByDt(
			@QueryParam("from") String from,
			@QueryParam("to") String to,
			@QueryParam("cityId") Long cityId)
		throws Exception;

	@GET
	@Path("/datas-between-date")
	List<Data> findByDtBetweenOrderByDt(
			@QueryParam("from") String from,
			@QueryParam("to") String to,
			@QueryParam("cityId") Long cityId,
			@QueryParam("orderByName") String orderByName,
			@QueryParam("orderByType") String orderByType)
		throws Exception;

}
