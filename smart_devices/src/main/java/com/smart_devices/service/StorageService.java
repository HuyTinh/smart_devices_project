package com.smart_devices.service;

import java.util.List;

import com.smart_devices.model.Storage;

public interface StorageService {
	Storage findById(int id);

	void deleteAllById(List<Integer> ids);

	Storage getById(Integer id);

	void deleteById(Integer id);

	List<Storage> findAll();

	Storage save(Storage entity);
}