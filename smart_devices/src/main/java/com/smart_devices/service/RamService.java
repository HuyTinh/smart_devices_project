package com.smart_devices.service;

import java.util.List;

import com.smart_devices.model.Ram;

public interface RamService {

	Ram findById(int id);

	void deleteAllById(List<Integer> ids);

	Ram getById(Integer id);

	void deleteById(Integer id);

	List<Ram> findAll();

	Ram save(Ram entity);
	
}
