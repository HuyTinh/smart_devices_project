package com.smart_devices.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.smart_devices.model.Product;

public interface ProductService {
	List<Product> findAll();
	
	Product findById(int id);
	
	Product findById(int id, Sort sort);

	Product findByLineOfProduct(String lineOfProduct);
	
	Product findByLineOfProduct(String lineOfProduct, Sort sort);
}

