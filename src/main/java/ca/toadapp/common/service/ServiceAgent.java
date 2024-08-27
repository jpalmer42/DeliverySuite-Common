package ca.toadapp.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.toadapp.common.data.repository.RepoAgent;
import ca.toadapp.common.data.repository.RepoAgentLocation;
import ca.toadapp.common.data.repository.RepoAgentNotification;

@Service
public class ServiceAgent {

	@Autowired
	private RepoAgent contextRepo;

	@Autowired
	private RepoAgentNotification contextNotificationRepo;

	@Autowired
	private RepoAgentLocation contextLocationRepo;

}
