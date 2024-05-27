package com.smart_devices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_devices.model.OperatingSystem;
import com.smart_devices.repository.OperatingSystemRepository;
import com.smart_devices.service.OperatingSystemService;

@Service
public class OperatingSystemServiceImpl implements OperatingSystemService {
	
	@Autowired
	OperatingSystemRepository operatingSystemRespository;

	@Override
	public OperatingSystem save(OperatingSystem entity) {
		return operatingSystemRespository.save(entity);
	}

	@Override
	public List<OperatingSystem> findAll() {
		return operatingSystemRespository.findAll();
	}

	@Override
	public void deleteById(Integer id) {
		operatingSystemRespository.deleteById(id);
	}

	@Override
	public OperatingSystem getById(Integer id) {
		return operatingSystemRespository.getById(id);
	}

	@Override
	public void deleteAllById(List<Integer> ids) {
		operatingSystemRespository.deleteAllById(ids);
	}
	
	@Override
	public OperatingSystem findById(int id) {
		return operatingSystemRespository.findById(id).get();
	}
	
}
