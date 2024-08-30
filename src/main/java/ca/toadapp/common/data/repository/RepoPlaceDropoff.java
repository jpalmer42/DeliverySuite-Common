package ca.toadapp.common.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.toadapp.common.data.entity.DaoPlaceDropoff;

public interface RepoPlaceDropoff extends JpaRepository<DaoPlaceDropoff, String> {

}
