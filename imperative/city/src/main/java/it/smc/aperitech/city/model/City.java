package it.smc.aperitech.city.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Mauro Celani
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class City {

	@Id
	private long id;
	private String name;
	private String country;
	private double lat;
	private double lon;

}
