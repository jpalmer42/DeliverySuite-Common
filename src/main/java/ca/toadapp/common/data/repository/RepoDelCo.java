package ca.toadapp.common.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ca.toadapp.common.data.entity.DaoDelCo;

public interface RepoDelCo extends JpaRepository<DaoDelCo, Long> {

	@Modifying
	@Query("UPDATE DaoDelCo d SET d.dispatcherId = :agentId WHERE d.id = :deliveryCompanyId")
	void updateDispatcher( Long deliveryCompanyId, Long agentId );

}
