package it.smc.aperitech.reactiveweather.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Mauro Celani
 */
@Data
@Document("weathers")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Weather {

	@Id
	private long id;
	private String main;
	private String icon;
	private String description;

}
