package ca.toadapp.common.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ca.toadapp.common.data.entity.DaoItem;

public interface RepoItem extends JpaRepository<DaoItem, Long> {

	@Modifying
	@Query("UPDATE DaoItem SET driverId = : driverId WHERE id = :itemId")
	void assignDriver( Long itemId, Long driverId );

}
