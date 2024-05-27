package com.smart_devices.service;

import java.util.List;

import com.smart_devices.model.OperatingSystem;

public interface OperatingSystemService {
	OperatingSystem findById(int id);

	void deleteAllById(List<Integer> ids);

	OperatingSystem getById(Integer id);

	void deleteById(Integer id);

	List<OperatingSystem> findAll();

	OperatingSystem save(OperatingSystem entity);
}
