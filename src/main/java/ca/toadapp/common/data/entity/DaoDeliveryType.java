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
@Table(name = "deliveryTypes")
public class DaoDeliveryType extends BaseEntity {

	@Column(nullable = false)
	private String	type;

	@Column(nullable = false)
	private Double	baseFee	= 0.0;
}
