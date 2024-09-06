package ca.toadapp.common.data.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import ca.toadapp.common.data.ConverterAgentRolesCollection;
import ca.toadapp.common.data.enumeration.AgentRoles;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "agents")
public class DaoAgent extends BaseEntity {
	private String agentId; // name@delCo`

	@Convert(converter = ConverterAgentRolesCollection.class)
	private Collection<AgentRoles> agentRoles;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime onDutySince;

	private String name;
	private String address;
	private String phone;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deliveryCompanyId", updatable = false, insertable = false)
	private DaoDelCo deliveryCompany;

	@Column(name = "deliveryCompanyId")//, updatable = false, insertable = false)
	private Long deliveryCompanyId;

	@Column(nullable = false, columnDefinition = "bool default 'false'")
	private Boolean acceptAutoDispatch = false;

	@Column(nullable = false, columnDefinition = "bool default 'false'")
	private Boolean hasSmartServe = false;

	@Column(nullable = false, columnDefinition = "bool default 'false'")
	private Boolean hasPaymentMachine = false;

}
