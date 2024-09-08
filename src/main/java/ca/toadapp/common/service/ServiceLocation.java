package ca.toadapp.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class ServiceLocation {

	@Value("${google.geo.apiKey:na}")
	private String			apiKey;

	private GeoApiContext	apiContext;

	@PostConstruct
	private void postConstruct() {
		apiContext = new GeoApiContext.Builder().apiKey( apiKey ).build();
	}

	@PreDestroy
	private void preDestory() {
		apiContext.shutdown();
	}

	Double calcDistance( Double p1Lat, Double p1Lng, Double p2Lat, Double p2Lng ) {
		Double ret = null;

		try {
			var p1 = new String[] { p1Lat.toString(), p1Lng.toString() };
			var p2 = new String[] { p2Lat.toString(), p2Lng.toString() };

			final var distanceMatrix = DistanceMatrixApi.getDistanceMatrix( apiContext, p1, p2 ).await();
			ret = distanceMatrix.rows[0].elements[0].distance.inMeters / 1000.0;
		}
		catch ( Exception ex ) {}

		return ret;
	}
	
}
