package it.smc.aperitech.proxy;

import it.smc.aperitech.dto.CityContainerModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author Mauro Celani
 */
public interface CityProxy {

	@POST
	@Path("/add-city")
	@Consumes("application/json")
	CompletableFuture<Void> addCity(CityContainerModel.City city);

	@POST
	@Path("/add-cities")
	@Consumes("application/json")
	CompletableFuture<Void> addCities(List<CityContainerModel.City> cities);

	@GET
	@Path("/city/{cityId}")
	Optional<CityContainerModel.City> getCity(@PathParam("cityId") long cityId);

	@GET
	@Path("/city-by-name/{cityName}")
	Collection<CityContainerModel.City> getCityByName(
		@PathParam("cityName") String cityName);

	@GET
	@Path("/city-by-name-like/{cityName}")
	Collection<CityContainerModel.City> getCityByNameLike(
		@PathParam("cityName") String cityName);

	@GET
	@Path("/city-by-name-ilike/{cityName}")
	Collection<CityContainerModel.City> getCityByNameILike(
		@PathParam("cityName") String cityName);

}
