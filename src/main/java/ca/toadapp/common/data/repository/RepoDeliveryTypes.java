package ca.toadapp.common.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.toadapp.common.data.entity.DaoDeliveryType;

public interface RepoDeliveryTypes extends JpaRepository<DaoDeliveryType, Long> {

}
