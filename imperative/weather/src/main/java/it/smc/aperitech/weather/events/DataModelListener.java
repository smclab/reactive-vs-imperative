package it.smc.aperitech.weather.events;

import it.smc.aperitech.weather.model.Data;
import it.smc.aperitech.weather.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

/**
 * @author Mauro Celani
 */
@Component
public class DataModelListener extends AbstractMongoEventListener<Data> {

	@Override
	public void onBeforeConvert(BeforeConvertEvent<Data> event) {

		if (event.getSource().getDataId() < 1) {

			event.getSource().setDataId(
				sequenceGenerator.generateSequence(Data.SEQUENCE_NAME));
		}
	}

	@Autowired
	private SequenceGeneratorService sequenceGenerator;

}