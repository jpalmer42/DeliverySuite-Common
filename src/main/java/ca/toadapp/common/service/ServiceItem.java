package ca.toadapp.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.toadapp.common.data.entity.DaoItem;
import ca.toadapp.common.data.repository.RepoItem;

@Service
public class ServiceItem {
	@Autowired
	private RepoItem contextRepo;

	public DaoItem save( DaoItem item ) {
		// TODO: check dropoff & pickup
		// TODO: Calc Distance from pickup to dropoff

		return contextRepo.save( item );
	}

}
