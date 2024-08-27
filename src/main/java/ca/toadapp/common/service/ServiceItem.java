package ca.toadapp.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.toadapp.common.data.repository.RepoItem;

@Service
public class ServiceItem {
	@Autowired
	private RepoItem contextRepo;

}
