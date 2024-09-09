package ca.toadapp.common.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.toadapp.common.data.entity.DaoDelCo;
import ca.toadapp.common.data.entity.DaoItem;
import ca.toadapp.common.data.entity.DaoPlaceDropoff;
import ca.toadapp.common.data.entity.DaoPlacePickup;
import ca.toadapp.common.data.enumeration.DeliveryState;
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
		item.setAssignmentInitial( LocalDateTime.now() );

		// Dropoff can be left unassigned, default values will be assigned.
		calcFees( item, delCo, pickup, dropoff );

		final var ret = contextRepo.save( item );

		calcTime( item, DeliveryState.assignmentInitial );

		return ret;
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

	@Transactional
	public DaoItem assignDriver( Long itemId, Long driverId ) throws MissingDataException, RecordNotFoundException {
		if( itemId == null )
			throw new MissingDataException( "itemId must not be null" );
		if( driverId == null )
			throw new MissingDataException( "driverId must not be null" );

		var item = contextRepo.findById( itemId ).orElse( null );
		if( item == null )
			throw new RecordNotFoundException( String.format( "Item [%ld] not found", itemId ) );

		if( driverId != item.getDriverId() ) {
			// TODO: Notify old driver
		}
		item.setDriverId( driverId );

		calcTime( item, DeliveryState.assignmentInitial );

		return contextRepo.save( item );

//		contextRepo.assignDriver( itemId, driverId );
	}

	@Transactional
	public DaoItem updateDeliveryState( Long itemId, DeliveryState state ) throws RecordNotFoundException, MissingDataException {
		if( itemId == null )
			throw new MissingDataException( "itemId must not be null" );

		var item = contextRepo.findById( itemId ).orElse( null );
		if( item == null )
			throw new RecordNotFoundException( String.format( "Item [%ld] not found", itemId ) );

		calcTime( item, state );

		return contextRepo.save( item );
	}

	private void calcTime( DaoItem item, DeliveryState state ) {
		var now = LocalDateTime.now();

		switch ( state ) {
			case requestCancelled:
				item.setRequestCancelled( now );
			// TODO: Cancellation Notification
			break;

			case requestInitial:
				item.setRequestInitial( now );
				if( item.getAssignmentAcknowledged() == null ) {
					// TODO: Notify Dispatcher
				}
				item.setAssignmentInitial( null );
				item.setAssignmentAcknowledged( null );
				item.setOnRouteInitial( null );
				item.setOnRouteETA( null );
				item.setPackagePickedUp( null );
				item.setPackageDeliveryETA( null );
				item.setPackageDelivered( null );
			break;

			case requestAcknowledged:
				item.setRequestAcknowledged( now );
				item.setAssignmentInitial( null );
				item.setAssignmentAcknowledged( null );
				item.setOnRouteInitial( null );
				item.setOnRouteETA( null );
				item.setPackagePickedUp( null );
				item.setPackageDeliveryETA( null );
				item.setPackageDelivered( null );
			break;

			case assignmentInitial:
				item.setAssignmentInitial( now );
				// TODO: Assignment Notification
				item.setAssignmentAcknowledged( null );
				item.setOnRouteInitial( null );
				item.setOnRouteETA( null );
				item.setPackagePickedUp( null );
				item.setPackageDeliveryETA( null );
				item.setPackageDelivered( null );
			break;

			case assignmentAcknowledged:
				item.setAssignmentAcknowledged( now );
				item.setOnRouteInitial( null );
				item.setOnRouteETA( null );
				item.setPackagePickedUp( null );
				item.setPackageDeliveryETA( null );
				item.setPackageDelivered( null );
			break;

			case onRouteInitial:
				item.setOnRouteInitial( now );
				item.setOnRouteETA( null );
				item.setPackagePickedUp( null );
				item.setPackageDeliveryETA( null );
				item.setPackageDelivered( null );
			break;

			case onRouteETA:
				item.setOnRouteETA( now );
				item.setPackagePickedUp( null );
				item.setPackageDeliveryETA( null );
				item.setPackageDelivered( null );
			break;

			case packagePickedUp:
				item.setPackagePickedUp( now );
				item.setPackageDeliveryETA( null );
				item.setPackageDelivered( null );
			break;

			case packageDeliveryETA:
				item.setPackageDeliveryETA( now );
				item.setPackageDelivered( null );
			break;

			case packageDelivered:
				item.setPackageDelivered( now );
			break;
		}
	}

}
