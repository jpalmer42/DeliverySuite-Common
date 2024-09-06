package ca.toadapp.common.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.toadapp.common.data.entity.DaoDeliveryType;
import ca.toadapp.common.data.repository.RepoDeliveryTypes;

@Service
public class ServiceDeliveryTypes {

	@Autowired
	private RepoDeliveryTypes deliveryTypes;

	Collection<DaoDeliveryType> get() {
		return deliveryTypes.findAll();
	}

	public Collection<DaoDeliveryType> saveAll(ArrayList<DaoDeliveryType> delTypes) {
		return deliveryTypes.saveAll(delTypes);
	}
}
