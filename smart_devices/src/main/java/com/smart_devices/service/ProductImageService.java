package com.smart_devices.service;

import java.util.List;

import com.smart_devices.model.ProductImage;

public interface ProductImageService {

	ProductImage getById(Integer id);

	void deleteById(Integer id);

	List<ProductImage> findAll();

	ProductImage save(ProductImage entity);

	List<String> findImagePathsByProductDetailId(Integer productDetailId);

}