package ca.toadapp.common.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.toadapp.common.data.entity.DaoPlace;

public interface RepoPlace extends JpaRepository<DaoPlace, Long> {

}
