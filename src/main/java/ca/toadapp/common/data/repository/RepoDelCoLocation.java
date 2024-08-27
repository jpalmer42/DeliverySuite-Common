package ca.toadapp.common.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.toadapp.common.data.entity.DaoDelCoLocation;

public interface RepoDelCoLocation extends JpaRepository<DaoDelCoLocation, Long> {

}
