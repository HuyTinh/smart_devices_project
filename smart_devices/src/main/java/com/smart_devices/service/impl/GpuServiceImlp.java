package com.smart_devices.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_devices.model.Gpu;
import com.smart_devices.repository.GpuRepository;
import com.smart_devices.service.GpuService;

@Service
public class GpuServiceImlp implements GpuService {

	@Autowired
	GpuRepository gpuRepository;
	
	@Override
	public Gpu findById(int id) {
		// TODO Auto-generated method stub
		return gpuRepository.findById(id).get();
	}

}
