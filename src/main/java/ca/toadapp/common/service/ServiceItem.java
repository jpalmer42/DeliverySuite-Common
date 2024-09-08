package ca.toadapp.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.toadapp.common.data.entity.DaoDelCo;
import ca.toadapp.common.data.entity.DaoItem;
import ca.toadapp.common.data.entity.DaoPlaceDropoff;
import ca.toadapp.common.data.entity.DaoPlacePickup;
import ca.toadapp.common.data.enumeration.PaymentTypes;
import ca.toadapp.common.data.repository.RepoItem;
import ca.toadapp.common.exceptions.MissingDataException;
import ca.toadapp.common.exceptions.RecordNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ServiceItem {
	@Autowired
	private RepoItem			contextRepo;

	@Autowired
	private ServiceDelCo		serviceDelCo;

	@Autowired
	private ServicePlacePickup	servicePlacePickup;

	@Autowired
	private ServicePlaceDropoff	servicePlaceDropoff;

	@Autowired
	private ServiceLocation		serviceLocation;

	@Transactional
	public DaoItem save( DaoItem item ) throws MissingDataException, RecordNotFoundException {
		// Validate dropoff & pickup
		String pickupId = item.getPickupPlaceId();
		Long delCoId = item.getDeliveryCompanyId();
		String dropoffId = item.getDropoffPlaceId();

		if( pickupId == null )
			throw new MissingDataException( "Pickup Location Required" );
		if( delCoId == null )
			throw new MissingDataException( "Delivery Company Required" );

		var delCo = serviceDelCo.getById( delCoId );
		if( delCo == null )
			throw new RecordNotFoundException( String.format( "Delivery Company [%s] not found", delCoId ) );

		var pickup = servicePlacePickup.getByPlaceId( pickupId );
		if( pickup == null )
			throw new RecordNotFoundException( String.format( "Pickup PlaceId [%s] not found", pickupId ) );

		var dropoff = servicePlaceDropoff.getByPlaceId( dropoffId );

		// Assign for
		item.setPickupPlace( pickup );
		item.setDropoffPlace( dropoff );
		item.setAutoAssignDriver( delCo.getHasAutoDispatch() );

		// Dropoff can be left unassigned, default values will be assigned.
		calcFees( item, delCo, pickup, dropoff );

		return contextRepo.save( item );
	}

	private void calcFees( DaoItem item, DaoDelCo delCo, DaoPlacePickup pickup, DaoPlaceDropoff dropoff ) throws RecordNotFoundException {

		// Calculate Distance and delivery cost
		var serviceArea = serviceDelCo.findServiceArea( delCo.getServiceAreas(), pickup.getLatitude(), pickup.getLongitude() );
		if( serviceArea == null ) {
			throw new RecordNotFoundException( String.format( "No DelCoLocations Defined for [%ld]", delCo.getId() ) );
		}

		if( pickup.getOnAccount() && pickup.getDeliveryCompanyId() == delCo.getId() ) {
			item.setDeliveryPaymentType( PaymentTypes.account );
			item.setDeliveryFee( 0.00 );
		}
		else {
			item.setDeliveryFee( serviceArea.getDeliveryFeeBase() );
		}

		// Are there overages?
		final Double kmDistance = dropoff == null ? 0.0 : serviceLocation.calcDistance( true, pickup.getLatitude(), pickup.getLongitude(), dropoff.getLatitude(), dropoff.getLongitude() );
		item.setDropoffDistance( kmDistance.intValue() );

		final var distanceBase = serviceArea.getDistanceBase();
		final var overage = kmDistance.intValue() - distanceBase;
		if( overage > 0 ) {
			item.setDeliveryPaymentType( PaymentTypes.accountPlus );
			var overageFee = overage * serviceArea.getDeliveryFeeBeyondBase();
			item.setDeliveryFee( item.getDeliveryFee() + overageFee );
		}

	}

}
