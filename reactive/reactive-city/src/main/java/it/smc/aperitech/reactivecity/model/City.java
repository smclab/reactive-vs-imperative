package it.smc.aperitech.reactivecity.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @author Cristian Bianco
 */
@Data
@NoArgsConstructor
public class City {

	@Id
	private long id;
	private String name;
	private String country;
	private double lat;
	private double lon;

}
