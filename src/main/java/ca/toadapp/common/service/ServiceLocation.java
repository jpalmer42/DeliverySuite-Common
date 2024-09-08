package ca.toadapp.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.java.Log;

@Log
@Service
public class ServiceLocation {
	private static final Double	R		= 6372.8;		// Radius of the earth in kilometers @ 30deg North
	private static final Double	PI180	= Math.PI / 180;

	@Value("${google.geo.apiKey:na}")
	private String				apiKey;

	private GeoApiContext		apiContext;

	@PostConstruct
	private void postConstruct() {
		apiContext = new GeoApiContext.Builder().apiKey( apiKey ).build();
	}

	@PreDestroy
	private void preDestory() {
		apiContext.shutdown();
	}

	Double calcDistance( boolean useMapAPI, Double latitude1, Double longitude1, Double latitude2, Double longitude2 ) {
		Double ret = 0.0;

		if( useMapAPI ) {
			try {
				var request = DistanceMatrixApi.newRequest( apiContext );
				var distanceMatrix = request //
						.origins( new LatLng( latitude1, longitude1 ) )//
						.destinations( new LatLng( latitude2, longitude2 ) )//
						.mode( TravelMode.DRIVING )//
						.await();
				ret = distanceMatrix.rows[0].elements[0].distance.inMeters / 1000.0;
			}
			catch ( Exception ex ) {
				log.warning( ex.getLocalizedMessage() );
				ret = calcDistance( false, latitude1, longitude1, latitude2, longitude2 );
			}
		}
		else {
			double latDistance = Math.pow( Math.sin( ( PI180 * ( latitude2 - latitude1 ) ) / 2 ), 2 );
			double lonDistance = Math.pow( Math.sin( ( PI180 * ( longitude2 - longitude1 ) ) / 2 ), 2 );
			double a = latDistance + Math.cos( PI180 * ( latitude1 ) ) * Math.cos( PI180 * ( latitude2 ) ) * lonDistance;
			double c = 2 * Math.atan2( Math.sqrt( a ), Math.sqrt( 1 - a ) );

			ret = R * c;
		}
		return ret;
	}

}
