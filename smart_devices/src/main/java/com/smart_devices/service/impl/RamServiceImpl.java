package com.smart_devices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_devices.model.Ram;
import com.smart_devices.repository.RamRepository;
import com.smart_devices.service.RamService;

@Service
public class RamServiceImpl implements RamService {
	
	@Autowired
	RamRepository ramRepository;

	@Override
	public Ram save(Ram entity) {
		return ramRepository.save(entity);
	}

	@Override
	public List<Ram> findAll() {
		return ramRepository.findAll();
	}

	@Override
	public void deleteById(Integer id) {
		ramRepository.deleteById(id);
	}

	@Override
	public Ram getById(Integer id) {
		return ramRepository.findById(id).get();
	}

	@Override
	public void deleteAllById(List<Integer> ids) {
		ramRepository.deleteAllById(ids);
	}
	@Override
	public Ram findById(int id) {
		return ramRepository.findById(id).get();
	}

	
}
