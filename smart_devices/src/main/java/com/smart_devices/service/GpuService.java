package com.smart_devices.service;

import java.util.List;

import com.smart_devices.model.Gpu;

public interface GpuService {
	 Gpu findById(int id);

	void deleteAllById(List<Integer> ids);

	Gpu getById(Integer id);

	void deleteById(Integer id);

	List<Gpu> findAll();

	Gpu save(Gpu entity);
}