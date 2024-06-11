package com.smart_devices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_devices.model.Gpu;
import com.smart_devices.repository.GpuRepository;
import com.smart_devices.service.GpuService;
@Service
public class GpuServiceImpl implements GpuService{

	@Autowired
	GpuRepository gpuRespository;

	@Override
	public  Gpu save(Gpu entity) {
		return gpuRespository.save(entity);
	}

	@Override
	public List<Gpu> findAll() {
		return gpuRespository.findAll();
	}

	@Override
	public void deleteById(Integer id) {
		gpuRespository.deleteById(id);
	}

	@Override
	public Gpu getById(Integer id) {
		return gpuRespository.findById(id).get();
	}

	@Override
	public void deleteAllById(List<Integer> ids) {
		gpuRespository.deleteAllById(ids);
	}
	
	@Override
	public Gpu findById(int id) {
		// TODO Auto-generated method stub
		return gpuRespository.findById(id).get();
	}
}
