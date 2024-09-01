package ca.toadapp.common.data.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.toadapp.common.data.entity.DaoAgent;

public interface RepoAgent extends JpaRepository<DaoAgent, Long> {

	Optional<DaoAgent> findByAgentId(String uid);

	@Query("UPDATE DaoAgent a set a.onDutySince = :localDateTime where id = :agentId")
	void setDutyStatus(Long agentId, LocalDateTime localDateTime);

}
