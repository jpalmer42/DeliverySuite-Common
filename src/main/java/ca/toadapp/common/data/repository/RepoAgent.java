package ca.toadapp.common.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.toadapp.common.data.entity.DaoAgent;

public interface RepoAgent extends JpaRepository<DaoAgent, Long> {

}
