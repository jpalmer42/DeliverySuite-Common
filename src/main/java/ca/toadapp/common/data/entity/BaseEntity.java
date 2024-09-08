package ca.toadapp.common.data.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public abstract class BaseEntity extends BaseEntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = null;
}
