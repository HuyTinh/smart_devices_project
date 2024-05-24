package com.smart_devices.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_devices.model.Ram;
import com.smart_devices.repository.RamRepository;
import com.smart_devices.service.RamService;

@Service
public class RamServiceImlp implements RamService {

	@Autowired
	RamRepository ramRepository;
	
	@Override
	public Ram findById(int id) {
		// TODO Auto-generated method stub
		return ramRepository.findById(id).get();
	}

}
