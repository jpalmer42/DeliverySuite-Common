package ca.toadapp.common.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "placesPickup")
public class DaoPlacePickup extends BaseEntityDate {
	
	@Id
	private String placeId; // Google Place Id

	private String name;
	private String address;
	private String phone;

	private Double latitude;
	private Double longitude;
	
	// ===============================================================
	// Pickup info
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "deliveryCompanyId")
	private DaoDelCo deliveryCompany;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "deliveryCompanyId", updatable = false, insertable = false)
	private Long deliveryCompanyId;

	@Column(nullable= false, columnDefinition = "bool default 'false'")
	private Boolean onAccount = false;	// Delivery fee is paid by pickup location.

}
