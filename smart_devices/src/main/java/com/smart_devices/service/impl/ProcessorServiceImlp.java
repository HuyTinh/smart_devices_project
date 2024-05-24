package com.smart_devices.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_devices.model.Processor;
import com.smart_devices.repository.ProcessorRepository;
import com.smart_devices.service.ProcessorService;

@Service
public class ProcessorServiceImlp implements ProcessorService {

	@Autowired
	ProcessorRepository processorRepository; 
	
	@Override
	public Processor findById(int id) {
		// TODO Auto-generated method stub
		return processorRepository.findById(id).get();
	}

}
