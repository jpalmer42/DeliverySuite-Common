package ca.toadapp.common.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "agentLocations")
public class DaoAgentLocation extends BaseEntity {

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "agentId")
	private DaoAgent agent;

	@Column(name = "agentId", updatable = false, insertable = false)
	private Long agentId;
	
	private Double latitude;
	private Double longitude;
}
