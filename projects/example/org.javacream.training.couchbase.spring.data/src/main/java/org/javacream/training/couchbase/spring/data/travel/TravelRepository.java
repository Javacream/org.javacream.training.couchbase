package org.javacream.training.couchbase.spring.data.travel;

import java.util.List;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface to manage {@link Airline} instances.
 *
 */
@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "airlines")
public interface TravelRepository extends CrudRepository<Airline, String> {

	/**
	 * Derived query selecting by {@code iataCode}.
	 *
	 * @param code
	 * @return
	 */
	Airline findAirlineByIataCode(String code);

	/**
	 * Query method using {@code airlines/all} view.
	 *
	 * @return
	 */
	@View(designDocument = "airlines", viewName = "allAirlines")
	List<Airline> findAllAirlinesBy();
}
