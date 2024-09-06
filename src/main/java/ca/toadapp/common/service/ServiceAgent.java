package ca.toadapp.common.service;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.toadapp.common.data.entity.DaoAgent;
import ca.toadapp.common.data.entity.DaoAgentLocation;
import ca.toadapp.common.data.entity.DaoAgentNotification;
import ca.toadapp.common.data.repository.RepoAgent;
import ca.toadapp.common.data.repository.RepoAgentLocation;
import ca.toadapp.common.data.repository.RepoAgentNotification;

@Service
public class ServiceAgent {

	@Autowired
	private RepoAgent contextRepo;

	@Autowired
	private RepoAgentNotification contextNotificationRepo;

	@Autowired
	private RepoAgentLocation contextLocationRepo;

	@Autowired
	private ServiceDelCo serviceDelCo;

	public DaoAgent getById(Long agentId) {
		var response = contextRepo.findById(agentId);

		return response.orElse(null);
	}

	public DaoAgent getByAgentId(String uid) {
		var response = contextRepo.findByAgentId(uid);

		return response.orElse(null);
	}

	public DaoAgent save(DaoAgent agent) {

		// Id DelCoId is provided, get the delCo before Save;
		if (agent.getDeliveryCompanyId() != null) {
			agent.setDeliveryCompany(serviceDelCo.getById(agent.getDeliveryCompanyId()));
		}

		var response = contextRepo.save(agent);

		return response;
	}

	public void setOnDuty(Long agentId, Boolean dutyStatus) {
		LocalDateTime ldt = dutyStatus == false ? null : LocalDateTime.now();
		contextRepo.setDutyStatus(agentId, ldt);
	}

	public DaoAgentLocation getLocation(Long agentId) {
		var response = contextLocationRepo.findById(agentId).orElse(null);
		return response;
	}

	public DaoAgentLocation setLocation(DaoAgentLocation agentLocation) {
		var response = contextLocationRepo.save(agentLocation);
		return response;
	}

	public Collection<DaoAgentNotification> getNotificationConfig(Long agentId) {
		var response = contextNotificationRepo.findAllByAgentId(agentId);
		return response;
	}

	public Collection<DaoAgentNotification> setNotificationConfig(Collection<DaoAgentNotification> agentNotifications) {
		if (!agentNotifications.isEmpty()) {
			contextNotificationRepo.deleteAllByAgentId(agentNotifications.iterator().next().getAgentId());
			var response = contextNotificationRepo.saveAll(agentNotifications);
			return response;
		}
		return null;
	}

}
