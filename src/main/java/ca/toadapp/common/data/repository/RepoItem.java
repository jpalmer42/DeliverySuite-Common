package ca.toadapp.common.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.toadapp.common.data.entity.DaoItem;

public interface RepoItem extends JpaRepository<DaoItem, Long> {

}
