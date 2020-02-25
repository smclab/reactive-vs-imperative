package it.smc.aperitech.reactiveweather.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author Mauro Celani
 */
@lombok.Data
@Document("datas")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Data {

	@Transient
	public static final String SEQUENCE_NAME = "data_sequence";

	@Id
	private long dataId;

	@Indexed
	private long cityId;

	@Indexed
	private LocalDateTime dt;

	// CityContainer.Main
	private double temp;
	private double tempMin;
	private double tempMax;
	private int humidity;

	private long weatherId;

	private int clouds;

	private double windSpeed;
	private double windDeg;

}
