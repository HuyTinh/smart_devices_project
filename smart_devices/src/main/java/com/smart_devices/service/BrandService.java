package com.smart_devices.service;

import java.util.List;

import com.smart_devices.model.Brand;

public interface BrandService {

	void deleteAllById(List<Integer> ids);

	Brand getById(Integer id);

	List<Brand> findAll();

	Brand save(Brand entity);

	void deleteById(Integer id);

}
