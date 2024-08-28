package ca.toadapp.common.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "deliveryTypes")
public class DaoDeliveryType {
	
	@Column(nullable = false)
	private String type;
	
	@Column(nullable = false)
	private Double baseFee = 0.0;
}
