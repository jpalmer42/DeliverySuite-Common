package ca.toadapp.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.toadapp.common.data.entity.DaoItem;
import ca.toadapp.common.data.repository.RepoItem;
import ca.toadapp.common.exceptions.MissingDataException;
import ca.toadapp.common.exceptions.RecordNotFoundException;

@Service
public class ServiceItem {
	@Autowired
	private RepoItem			contextRepo;

	@Autowired
	private ServicePlacePickup	servicePlacePickup;

	@Autowired
	private ServicePlaceDropoff	servicePlaceDropoff;

	public DaoItem save( DaoItem item ) throws MissingDataException, RecordNotFoundException {
		// Validate dropoff & pickup
		String pickupId = item.getPickupPlaceId();
		String dropoffId = item.getDropoffPlaceId();
		if( pickupId == null )
			throw new MissingDataException( "Pickup Location Required" );
		if( dropoffId == null )
			throw new MissingDataException( "Dropof Location Required" );

		var pickup = servicePlacePickup.getByPlaceId( pickupId );
		if( pickup == null )
			throw new RecordNotFoundException( String.format( "Pickup PlaceId %s not found", pickupId ) );

		var dropoff = servicePlaceDropoff.getByPlaceId( dropoffId );
		if( dropoff == null )
			throw new RecordNotFoundException( String.format( "Dropoff PlaceId %s not found", dropoffId ) );

		item.setPickupPlace( pickup );
		item.setDropoffPlace( dropoff );

		// Calc Costs & Distance
		if( pickup.getOnAccount() && pickup.getDeliveryCompanyId() == item.getDeliveryCompanyId() ) {
			
		}

		// Calc Distance from pickup to dropoff

		return contextRepo.save( item );
	}

}
