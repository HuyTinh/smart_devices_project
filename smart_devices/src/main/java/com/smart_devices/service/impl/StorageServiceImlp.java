package com.smart_devices.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_devices.model.Storage;
import com.smart_devices.repository.StorageRepository;
import com.smart_devices.service.StorageService;

@Service
public class StorageServiceImlp implements StorageService {

	@Autowired
	StorageRepository storageRepository;
	
	@Override
	public Storage findById(int id) {
		// TODO Auto-generated method stub
		return storageRepository.findById(id).get();
	}	
}
