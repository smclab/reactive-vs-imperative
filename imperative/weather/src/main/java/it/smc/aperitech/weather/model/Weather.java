package it.smc.aperitech.weather.model;

import it.smc.aperitech.dto.CityContainer;
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
public class Weather {

	@Id
	private long id;
	private String main;
	private String icon;
	private String description;

	public Weather(CityContainer.Weather weather) {
		this.id = weather.getId();
		this.main = weather.getMain();
		this.icon = weather.getIcon();
		this.description = weather.getDescription();
	}

}
