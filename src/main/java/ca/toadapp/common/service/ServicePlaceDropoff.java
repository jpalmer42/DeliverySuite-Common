package ca.toadapp.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.toadapp.common.data.entity.DaoPlaceDropoff;
import ca.toadapp.common.data.repository.RepoPlaceDropoff;

@Service
public class ServicePlaceDropoff {

	@Autowired
	private RepoPlaceDropoff contextRepo;

	public DaoPlaceDropoff save( DaoPlaceDropoff dropoff ) {
		return contextRepo.save( dropoff );
	}

	public DaoPlaceDropoff getByPlaceId( String placeId ) {
		if( placeId == null )
			return null;

		return contextRepo.findById( placeId ).orElse( null );
	}
}
