package ca.toadapp.common.data.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntityDate {

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime dateCreated;

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime dateUpdated;

	@JsonInclude(Include.NON_NULL)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime dateEnd = null;

	@PreUpdate
	@PrePersist
	public void setUpdatedDate() {
		dateUpdated = LocalDateTime.now();
	}
}
