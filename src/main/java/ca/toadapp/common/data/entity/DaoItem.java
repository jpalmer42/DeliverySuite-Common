package ca.toadapp.common.data.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import ca.toadapp.common.data.enumeration.PaymentTypes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "items")
public class DaoItem extends BaseEntity {

	// ===============================================================
	// Pickup Details
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pickupPlaceId")
	private DaoPlacePickup pickupPlace;

	@Column(name = "pickupPlaceId", updatable = false, insertable = false)
	private String pickupPlaceId;

	private String pickupAddress2;
	private String pickupInstructions;
	
	// ===============================================================
	// Dropoff Details
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dropoffPlaceId")
	private DaoPlaceDropoff dropoffPlace;

	@Column(name = "dropoffPlaceId", updatable = false, insertable = false)
	private String dropoffPlaceId;

	private String dropoffAddress2;
	private String dropoffInstructions;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime requestedPickupTime;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime requestedDropoffTime;

	// ===============================================================
	// Logistics
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "deliveryCompanyId")
	private DaoDelCo deliveryCompany;

	@Column(name = "deliveryCompanyId", updatable = false, insertable = false)
	private Long deliveryCompanyId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "driverId")
	private DaoAgent driver;

	@Column(name = "driverId", updatable = false, insertable = false)
	private Long driverId;

	private Integer pickupDistance; // Distance assigned driver must cover
	private Integer dropoffDistance; // Distance from pickup to dropoff

	@Column(nullable = false, columnDefinition = "bool default 'false'")
	private Boolean autoAssignDriver = false;

	// ===============================================================
	// Transaction Info
	@Column(nullable = false, columnDefinition = "bool default 'false'")
	private Boolean deliveryPaidByAccount = false;

	@Column(nullable = false)
	private Double goodsCost = 0.0;
	
	@Enumerated(EnumType.STRING)
	private PaymentTypes goodsPaymentType = PaymentTypes.unknown;

	@Column(nullable = false)
	private Double deliveryFee = 0.0;
	@Enumerated(EnumType.STRING)
	private PaymentTypes deliveryPaymentType = PaymentTypes.unknown;

	@Column(nullable = false)
	private Double deliveryTip = 0.0;
	@Enumerated(EnumType.STRING)
	private PaymentTypes deliveryTipPaymentType = PaymentTypes.unknown;

	// ===============================================================
	// Delivery Flags
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime requestCancelled;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime requestInitial;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime requestAcknowledged;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime assignmentInitial;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime assignmentAcknowledged;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime onRouteInitial;

}
