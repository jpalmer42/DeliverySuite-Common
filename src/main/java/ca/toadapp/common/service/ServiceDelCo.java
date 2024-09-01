package ca.toadapp.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.toadapp.common.data.entity.DaoDelCo;
import ca.toadapp.common.data.repository.RepoDelCo;
import ca.toadapp.common.data.repository.RepoDelCoHour;
import ca.toadapp.common.data.repository.RepoDelCoLocation;

@Service
public class ServiceDelCo {

	@Autowired
	private RepoDelCo contextRepo;

	@Autowired
	private RepoDelCoHour contextHourRepo;

	@Autowired
	private RepoDelCoLocation contextLocationRepo;

	public DaoDelCo getById(Long deliveryCompanyId) {
		var response = contextRepo.findById(deliveryCompanyId);
		
		return response.orElse(null);
	}

}
