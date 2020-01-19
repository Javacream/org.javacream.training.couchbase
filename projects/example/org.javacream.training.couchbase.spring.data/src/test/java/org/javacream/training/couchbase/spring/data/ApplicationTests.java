package org.javacream.training.couchbase.spring.data;

import java.util.List;
import java.util.Optional;

import org.javacream.training.couchbase.spring.data.travel.Airline;
import org.javacream.training.couchbase.spring.data.travel.TravelRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.couchbase.core.CouchbaseOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {


	@Autowired
	TravelRepository airlineRepository;

	@Autowired private CouchbaseCluster couchbaseCluster;
	@Autowired CouchbaseOperations couchbaseOperations;

	@Before
	public void before() {
		airlineRepository.findById("LH").ifPresent(couchbaseOperations::remove);
	}

	@Test
	public void shouldFindAirlineN1ql() {

		Airline airline = airlineRepository.findAirlineByIataCode("TQ");

		Assert.assertEquals((airline.getCallsign()), "TXW");
	}

	@Test
	public void shouldFindById() {

		Airline airline = airlineRepository.findAirlineByIataCode("TQ");
		Optional<Airline> searchedAirline = airlineRepository.findById(airline.getId());
		Assert.assertEquals(searchedAirline.get(), airline);
		Assert.assertFalse(airlineRepository.findById("unknown").isPresent());
	}

	@Test
	public void shouldFindByView() {

		List<Airline> airlines = airlineRepository.findAllAirlinesBy();
		System.out.println(airlines.size());
		Assert.assertTrue(airlines.size() == 187);
	}

	@Test
	public void shouldCreateAirline() {

		Airline airline = new Airline();

		airline.setId("LH");
		airline.setIataCode("LH");
		airline.setIcao("DLH");
		airline.setCallsign("Lufthansa");
		airline.setName("Lufthansa");
		airline.setCountry("Germany");

		airlineRepository.save(airline);

		Assert.assertEquals(airlineRepository.findById("LH").get(),airline);
	}
	
	@Test public void showDesignDocuments() {
		Bucket bucket = couchbaseCluster.openBucket("travel-sample");
		System.out.println(bucket.bucketManager().getDesignDocument("airlines"));
	}
}