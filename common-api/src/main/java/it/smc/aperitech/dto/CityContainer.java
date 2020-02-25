package it.smc.aperitech.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CityContainer {

	private City city;
	private List<Data> data;

	@lombok.Data
	@NoArgsConstructor
	public static class City {
		private long id;
		private String name;
		private String country;
		private LatLon coord;
	}

	@lombok.Data
	@NoArgsConstructor
	public static class Data {
		private LocalDateTime dt;
		private Main main;
		private List<Weather> weather;
		private Clouds clouds;
		private Wind wind;
	}

	@lombok.Data
	@NoArgsConstructor
	public static class Weather {
		private long id;
		private String main;
		private String icon;
		private String description;
	}

	@lombok.Data
	@NoArgsConstructor
	public static class Main {
		private double temp;
		private double temp_min;
		private double temp_max;
		private int humidity;
	}

	@lombok.Data
	@NoArgsConstructor
	public static class LatLon {
		private double lon;
		private double lat;
	}

	@lombok.Data
	@NoArgsConstructor
	public static class Clouds {
		private int all;
	}

	@lombok.Data
	@NoArgsConstructor
	public static class Wind {
		private double speed;
		private double deg;
	}

}
