package it.smc.aperitech.reactiveweather.services;

import it.smc.aperitech.reactiveweather.model.DatabaseSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Cristian Bianco
 */
@Service
@RequiredArgsConstructor
public class SequenceGeneratorService {

	public long generateSequence(String seqName) {

		DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
			new Update().inc("seq",1), options().returnNew(true).upsert(true),
			DatabaseSequence.class);

		return !Objects.isNull(counter) ? counter.getSeq() : 1;
	}

	private final MongoOperations mongoOperations;

}
