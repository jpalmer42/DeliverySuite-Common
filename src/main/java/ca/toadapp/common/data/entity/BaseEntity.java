package ca.toadapp.common.data.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@MappedSuperclass
public abstract class BaseEntity extends BaseEntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = null;

//	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
//	@JsonSerialize(using = LocalDateTimeSerializer.class)
//	private LocalDateTime dateCreated;
//
//	@Column(columnDefinition = "TIMESTAMP", nullable = false)
//	@JsonSerialize(using = LocalDateTimeSerializer.class)
//	private LocalDateTime dateUpdated;
//
//	@JsonInclude(Include.NON_NULL)
//	@JsonSerialize(using = LocalDateTimeSerializer.class)
//	private LocalDateTime dateEnd = null;
//
//	@PreUpdate
//	@PrePersist
//	public void setUpdatedDate() {
//		dateUpdated = LocalDateTime.now();
//	}
}
