package ca.toadapp.common.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "agentLocations")
public class DaoAgentLocation extends BaseEntity {

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agentId", updatable = false, insertable = false)
	private DaoAgent	agent;

	@Column(name = "agentId")
	private Long		agentId;

	private Double		latitude;
	private Double		longitude;
}
