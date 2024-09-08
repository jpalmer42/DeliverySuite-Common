package ca.toadapp.common.data.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import ca.toadapp.common.data.enumeration.DeliveryState;
import ca.toadapp.common.data.enumeration.PaymentTypes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "items")
public class DaoItem extends BaseEntity {

	// ===============================================================
	// Pickup Details
//	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pickupPlaceId", updatable = false, insertable = false)
	private DaoPlacePickup	pickupPlace;

	@Column(name = "pickupPlaceId")
	private String			pickupPlaceId;

	private String			pickupAddress2;
	private String			pickupInstructions;

	// ===============================================================
	// Dropoff Details
//	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dropoffPlaceId", updatable = false, insertable = false)
	private DaoPlaceDropoff	dropoffPlace;

	@Column(name = "dropoffPlaceId")
	private String			dropoffPlaceId;

	private String			dropoffAddress2;
	private String			dropoffInstructions;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime	requestedPickupTime;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime	requestedDropoffTime;

	// ===============================================================
	// Logistics
//	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deliveryCompanyId", updatable = false, insertable = false)
	private DaoDelCo		deliveryCompany;

	@Column(name = "deliveryCompanyId")
	private Long			deliveryCompanyId;

//	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driverId", updatable = false, insertable = false)
	private DaoAgent		driver;

	@Column(name = "driverId")
	private Long			driverId;

	private Integer			pickupDistance;									// Distance assigned driver must cover
	private Integer			dropoffDistance;								// Distance from pickup to dropoff

	@Column(nullable = false, columnDefinition = "bool default 'false'")
	private Boolean			autoAssignDriver		= false;

	@Column(nullable = false, columnDefinition = "bool default 'false'")
	private Boolean			requiresSmartServe		= false;				// There is a controlled substance in this delivery. (Smoke, Alcohol, Drugs)

	// ===============================================================
	// Transaction Info
	@Column(nullable = false, columnDefinition = "float default '0.00'")
	private Double			goodsCost				= 0.0;					// If not prepaid, then dropoff place has to pay this amount on top of the deliveryFee and agreed deliveryTip.

	@Enumerated(EnumType.STRING)
	private PaymentTypes	goodsPaymentType		= PaymentTypes.unknown;

	@Column(nullable = false, columnDefinition = "float default '0.00'")
	private Double			deliveryFee				= 0.0;					// Calculated from DeliveryType plus any extra distance beyond the delivery companies set limits.

	@Enumerated(EnumType.STRING)
	private PaymentTypes	deliveryPaymentType		= PaymentTypes.unknown;	// Only copied from pickup place if Delivery Companies are the same.

	@Column(nullable = false, columnDefinition = "float default '0.00'")
	private Double			deliveryTip				= 0.0;

	@Enumerated(EnumType.STRING)
	private PaymentTypes	deliveryTipPaymentType	= PaymentTypes.unknown;

	// ===============================================================
	// Delivery Flags
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime	requestCancelled;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime	requestInitial;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime	requestAcknowledged;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime	assignmentInitial;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime	assignmentAcknowledged;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime	onRouteInitial;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime	onRouteETA;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime	packagePickedUp;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime	packageDeliveryETA;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime	packageDelivered;

	@Transient
	DeliveryState getDeliveryState() {
		return requestCancelled != null ? DeliveryState.requestCancelled : //
				packageDelivered != null ? DeliveryState.packageDelivered : //
						packageDeliveryETA != null ? DeliveryState.packageDeliveryETA : //
								packagePickedUp != null ? DeliveryState.packagePickedUp : //
										onRouteETA != null ? DeliveryState.onRouteETA : //
												onRouteInitial != null ? DeliveryState.onRouteInitial : //
														assignmentAcknowledged != null ? DeliveryState.assignmentAcknowledged : //
																assignmentInitial != null ? DeliveryState.assignmentInitial : //
																		requestAcknowledged != null ? DeliveryState.requestAcknowledged : //
																				DeliveryState.requestInitial; //
	}

}
