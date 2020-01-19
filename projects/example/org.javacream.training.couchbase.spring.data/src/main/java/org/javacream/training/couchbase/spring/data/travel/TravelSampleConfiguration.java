package org.javacream.training.couchbase.spring.data.travel;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.core.CouchbaseOperations;

import com.couchbase.client.java.query.N1qlQuery;

@Configuration
public class TravelSampleConfiguration {
	@Autowired
	private CouchbaseOperations couchbaseOperations;

	@PostConstruct
	private void postConstruct() {

		// Need to post-process travel data to add _class attribute
		List<Airline> airlinesWithoutClassAttribute = couchbaseOperations.findByN1QL(N1qlQuery.simple( //
				"SELECT META(`travel-sample`).id AS _ID, META(`travel-sample`).cas AS _CAS, `travel-sample`.* " + //
						"FROM `travel-sample` " + //
						"WHERE type = \"airline\" AND _class IS MISSING;"),
				Airline.class);

		airlinesWithoutClassAttribute.forEach(couchbaseOperations::save);
	}
	
}
