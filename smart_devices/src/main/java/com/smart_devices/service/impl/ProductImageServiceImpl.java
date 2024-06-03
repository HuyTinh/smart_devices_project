package com.smart_devices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_devices.model.ProductImage;
import com.smart_devices.repository.ProductImageRepository;
import com.smart_devices.service.ProductImageService;

@Service
public class ProductImageServiceImpl implements ProductImageService {

	@Autowired
	ProductImageRepository productImageRepository;

	@Override
	public ProductImage save(ProductImage entity) {
		return productImageRepository.save(entity);
	}

	@Override
	public List<ProductImage> findAll() {
		return productImageRepository.findAll();
	}

	@Override
	public void deleteById(Integer id) {
		productImageRepository.deleteById(id);
	}

	@Override
	public ProductImage getById(Integer id) {
		return productImageRepository.getById(id);
	}

	@Override
	public List<String> findImagePathsByProductDetailId(Integer productDetailId) {
		return productImageRepository.findImagePathsByProductDetailId(productDetailId);
	}

	
	
}
