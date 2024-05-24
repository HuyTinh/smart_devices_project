package com.smart_devices.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_devices.model.OperatingSystem;
import com.smart_devices.repository.OperatingSystemRepository;
import com.smart_devices.service.OperatingSystemService;

@Service
public class OperatingSystemServiceImlp implements OperatingSystemService {

	@Autowired
	OperatingSystemRepository operatingSystemRepository;
	
	@Override
	public OperatingSystem findById(int id) {
		// TODO Auto-generated method stub
		return operatingSystemRepository.findById(id).get();
	}

}
