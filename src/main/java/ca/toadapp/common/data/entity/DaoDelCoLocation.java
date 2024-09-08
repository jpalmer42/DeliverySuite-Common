package ca.toadapp.common.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "deliveryCompanyLocations")
public class DaoDelCoLocation extends BaseEntity {

	@Column(nullable = false)
	private Double	latitude;
	@Column(nullable = false)
	private Double	longitude;

	@Column(nullable = false)
	private Integer	distanceBase			= 5;	// General Serviceable Area from Lat/Lng point

	@Column(nullable = false)
	private Double	deliveryFeeBase			= 5.0;	// Fee charged from Pickup to Dropoff
	@Column(nullable = false)
	private Double	deliveryFeeBeyondBase	= 1.0;	// per Kilometer

	@Column(nullable = false)
	private Double	dispatchFee				= 1.0;	// Fee paid to dispatcher.
}
