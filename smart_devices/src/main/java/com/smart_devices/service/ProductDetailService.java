package com.smart_devices.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.smart_devices.model.Product;
import com.smart_devices.model.ProductDetail;

public interface ProductDetailService {
	
	ProductDetail findById(int id);
	
	ProductDetail findByTitle(String title);
	
	List<ProductDetail> findByProduct(Product product, Sort sort);

	List<ProductDetail> findAll();

}
