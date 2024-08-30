package ca.toadapp.common.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.toadapp.common.data.entity.DaoPlacePickup;

public interface RepoPlacePickup extends JpaRepository<DaoPlacePickup, String> {

}
