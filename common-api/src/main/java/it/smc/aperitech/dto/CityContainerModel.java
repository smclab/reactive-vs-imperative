package it.smc.aperitech.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor(staticName = "of")
public class CityContainerModel {

	private City city;
	private Collection<Data> data;

	@lombok.Data
	@NoArgsConstructor
	public static class City {
		private long id;
		private String name;
		private String country;
		private double lat;
		private double lon;
	}

	@lombok.Data
	@NoArgsConstructor
	public static class Data {
		private LocalDateTime dt;
		private long dataId;
		private long cityId;
		private double temp;
		private double tempMin;
		private double tempMax;
		private int humidity;
		private long weatherId;
		private int clouds;
		private double windSpeed;
		private double windDeg;
	}

	@lombok.Data
	@NoArgsConstructor
	public static class Weather {
		private long id;
		private String main;
		private String icon;
		private String description;
	}

}
