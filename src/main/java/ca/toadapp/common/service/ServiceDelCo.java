package ca.toadapp.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.toadapp.common.data.entity.DaoDelCo;
import ca.toadapp.common.data.repository.RepoDelCo;
import jakarta.transaction.Transactional;

@Service
public class ServiceDelCo {

	@Autowired
	private RepoDelCo contextRepo;

//	@Autowired
//	private RepoDelCoHour contextHourRepo;
//
//	@Autowired
//	private RepoDelCoLocation contextLocationRepo;
//
//	@Autowired
//	private RepoDelCoBranding contextBrandingRepo;

//	@Autowired
//	private ServiceAgent serviceAgent;

	public DaoDelCo getById(Long deliveryCompanyId) {
		var response = contextRepo.findById(deliveryCompanyId);

		return response.orElse(null);
	}

	public DaoDelCo save(DaoDelCo delCo, boolean updateHours, boolean updateLocaitons, boolean updateBranding) {
		if (delCo.getId() != null) {
			final var origDelCo = getById(delCo.getId());

			if (origDelCo != null) {
				if (updateHours == false) {
					delCo.setHours(origDelCo.getHours());
				}

				if (updateLocaitons == false) {
					delCo.setServiceAreas(origDelCo.getServiceAreas());
				}

				if (updateBranding == false) {
					delCo.setBranding(origDelCo.getBranding());
				}

			}
		}

		return contextRepo.save(delCo);
	}

	@Transactional
	public void updateDispatcher(Long deliveryCompanyId, Long agentId) {
		contextRepo.updateDispatcher(deliveryCompanyId, agentId);
	}

}
