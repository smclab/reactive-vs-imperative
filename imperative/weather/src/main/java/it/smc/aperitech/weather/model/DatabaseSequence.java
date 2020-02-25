package it.smc.aperitech.weather.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Mauro Celani
 */
@Data
@Document(collection = "database_sequences")
public class DatabaseSequence {

	@Id
	private String id;
	private long seq;

}
