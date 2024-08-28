package ca.toadapp.common.data.entity;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "deliveryCompanyHours")
public class DaoDelCoHour extends BaseEntity {

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private DayOfWeek dayOfWeek; // 0-Monday

	@Column(nullable = false)
	private LocalTime timeStart;

	@Column(nullable = false)
	private LocalTime timeEnd;

}
