package com.smart_devices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_devices.model.Category;
import com.smart_devices.repository.CategoryRepository;
import com.smart_devices.service.CategoryService;
@Service
public class CategoryServiceImlp implements CategoryService{
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Category save(Category entity) {
		return categoryRepository.save(entity);
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public void deleteById(Integer id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public Category getById(Integer id) {
		return categoryRepository.findById(id).get();
	}

	@Override
	public void deleteAllById(List<Integer> ids) {
		categoryRepository.deleteAllById(ids);
	}
	
	
	
}
