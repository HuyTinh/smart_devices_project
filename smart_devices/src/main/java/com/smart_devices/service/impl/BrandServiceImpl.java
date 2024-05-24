package com.smart_devices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_devices.model.Brand;
import com.smart_devices.repository.BrandRepository;
import com.smart_devices.service.BrandService;
@Service
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	BrandRepository brandRespository;

	@Override
	public Brand  save(Brand entity) {
		return brandRespository.save(entity);
	}

	@Override
	public List<Brand> findAll() {
		return brandRespository.findAll();
	}

	@Override
	public Brand getById(Integer id) {
		return brandRespository.getById(id);
	}

	@Override
	public void deleteAllById(List<Integer> ids) {
		brandRespository.deleteAllById(ids);
	}
	
	
}
