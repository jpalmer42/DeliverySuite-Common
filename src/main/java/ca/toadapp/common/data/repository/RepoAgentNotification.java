package ca.toadapp.common.data.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.toadapp.common.data.entity.DaoAgentNotification;

public interface RepoAgentNotification extends JpaRepository<DaoAgentNotification, Long> {

	Collection<DaoAgentNotification> findAllByAgentId( Long agentId );

	void deleteAllByAgentId( Long agentId );

}
