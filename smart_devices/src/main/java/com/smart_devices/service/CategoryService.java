package com.smart_devices.service;

import java.util.List;

import com.smart_devices.model.Category;

public interface CategoryService {

	void deleteAllById(List<Integer> ids);

	Category getById(Integer id);

	void deleteById(Integer id);

	List<Category> findAll();

	Category save(Category entity);

}
