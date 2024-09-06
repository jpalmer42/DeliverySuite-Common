package ca.toadapp.common.data.entity;

import ca.toadapp.common.data.enumeration.NotificationTypes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "agent_notifications", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "agent_id", "method", "target" }) })
public class DaoAgentNotification extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agentId", updatable = false, insertable = false)
	private DaoAgent agent;

	@Column(name = "agentId")//, updatable = false, insertable = false)
	private Long agentId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "varchar(255) default 'none'")
	private NotificationTypes method = NotificationTypes.none; // none, sms, gcm
	
	@Column(nullable = false)
	private String target; // Cell # or GCM Token

	@Column(nullable = false, columnDefinition = "bool default 'true'")
	private Boolean enabled = true;

}
