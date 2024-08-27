package ca.toadapp.common.data.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import ca.toadapp.common.data.enumeration.PaymentTypes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "items")
public class DaoItem extends BaseEntity {

	private String pickupPlaceId; // Google Place ID
	private String pickupName;
	private String pickupAddress;
	private String pickupPhone;
	private String pickupInstructions;

	private String dropoffPlaceId; // Google Place ID
	private String dropoffName;
	private String dropoffAddress;
	private String dropoffPhone;
	private String dropoffInstructions;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime requestedPickupTime;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime requestedDropoffTime;

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
	private Boolean deliveryPaidByAccount = false;

	@Column(nullable = false, columnDefinition = "bool default 'false'")
	private Boolean autoAssignDriver = false;

	@Column(nullable = false)
	private Double payloadCost = 0.0;
	@MapKeyEnumerated(EnumType.STRING)
	private PaymentTypes payloadPaymentType = PaymentTypes.unknown;

	@Column(nullable = false)
	private Double deliveryFee = 0.0;
	@MapKeyEnumerated(EnumType.STRING)
	private PaymentTypes deliveryPaymentType = PaymentTypes.unknown;

	@Column(nullable = false)
	private Double deliveryTip = 0.0;
	@MapKeyEnumerated(EnumType.STRING)
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
