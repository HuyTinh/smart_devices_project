package com.smart_devices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_devices.model.Processor;
import com.smart_devices.repository.ProcessorRepository;
import com.smart_devices.service.ProcessorService;

@Service
public class ProcessorServiceImpl implements ProcessorService{
	
	@Autowired
	ProcessorRepository processorRepository;

	@Override
	public Processor save(Processor entity) {
		return processorRepository.save(entity);
	}

	@Override
	public List<Processor> findAll() {
		return processorRepository.findAll();
	}

	@Override
	public void deleteById(Integer id) {
		processorRepository.deleteById(id);
	}

	@Override
	public Processor getById(Integer id) {
		return processorRepository.getById(id);
	}

	@Override
	public void deleteAllById(List<Integer> ids) {
		processorRepository.deleteAllById(ids);
	}
	
}
