package ca.toadapp.common.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "placesDropoff")
public class DaoPlaceDropoff extends BaseEntityDate {

	@Id
	private String	placeId;	// Google Place Id

	private String	name;
	private String	address;
	private String	phone;

	private Double	latitude;
	private Double	longitude;
}
