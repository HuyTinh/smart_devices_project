package com.smart_devices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_devices.model.Storage;
import com.smart_devices.repository.StorageRepository;
import com.smart_devices.service.StorageService;

@Service
public class StorageServiceImpl implements StorageService{
	
	@Autowired
	StorageRepository storageRepository;

	@Override
	public Storage save(Storage entity) {
		return storageRepository.save(entity);
	}

	@Override
	public List<Storage> findAll() {
		return storageRepository.findAll();
	}

	@Override
	public void deleteById(Integer id) {
		storageRepository.deleteById(id);
	}

	@Override
	public Storage getById(Integer id) {
		return storageRepository.findById(id).get();
	}

	@Override
	public void deleteAllById(List<Integer> ids) {
		storageRepository.deleteAllById(ids);
	}

	@Override
	public Storage findById(int id) {
		return storageRepository.findById(id).get();
	}
	
	
	
}
