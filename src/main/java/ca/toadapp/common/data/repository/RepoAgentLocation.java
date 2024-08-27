package ca.toadapp.common.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.toadapp.common.data.entity.DaoAgentLocation;

public interface RepoAgentLocation extends JpaRepository<DaoAgentLocation, Long> {

}
