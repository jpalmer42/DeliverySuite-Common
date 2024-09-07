package ca.toadapp.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.toadapp.common.data.entity.DaoPlacePickup;
import ca.toadapp.common.data.repository.RepoPlacePickup;

@Service
public class ServicePlacePickup {

	@Autowired
	private RepoPlacePickup contextRepo;

	public DaoPlacePickup save( DaoPlacePickup pickup ) {
		return contextRepo.save( pickup );
	}
}
