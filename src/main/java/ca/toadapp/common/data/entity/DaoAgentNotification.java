package ca.toadapp.common.data.entity;

import ca.toadapp.common.data.enumeration.NotificationTypes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "agent_notifications", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "agent_id", "method", "target" }) })
public class DaoAgentNotification extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "agent_id")
	private DaoAgent agent;

	@Column(name = "agent_id", updatable = false, insertable = false)
	private Long agentId;

	@Enumerated(EnumType.STRING)
	@Column(nullable= false)
	private NotificationTypes method; // SMS, GCM
	private String target; // Cell # or GCM Token

	@Column(nullable= false, columnDefinition = "bool default 'true'")
	private Boolean enabled = true;

}
