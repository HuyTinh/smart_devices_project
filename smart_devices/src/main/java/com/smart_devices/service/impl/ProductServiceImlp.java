package com.smart_devices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.smart_devices.model.Product;
import com.smart_devices.repository.ProductDetailRespository;
import com.smart_devices.repository.ProductRespository;
import com.smart_devices.service.ProductService;

@Service
public class ProductServiceImlp implements ProductService {
	
	@Autowired
	ProductRespository productRespository;
	
	@Autowired
	ProductDetailRespository productDetailRespository;
	
	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return productRespository.findAll();
	}

	@Override
	public Product findById(int id) {
		// TODO Auto-generated method stub
		return productRespository.findById(id).get();
	}

	@Override
	public Product findById(int id, Sort sort) {
		// TODO Auto-generated method stub
		Product product = productRespository.findById(id).get();
		product.setProductDetails(productDetailRespository.findByProduct(product, sort));
		return product;
	}

	@Override
	public Product findByLineOfProduct(String lineOfProduct) {
		// TODO Auto-generated method stub
		return productRespository.findByLineOfProduct(lineOfProduct).get();
	}
	
	@Override
	public Product findByLineOfProduct(String lineOfProduct, Sort sort) {
		// TODO Auto-generated method stub
		Product product = productRespository.findByLineOfProduct(lineOfProduct).get();
		product.setProductDetails(productDetailRespository.findByProduct(product, sort));
		return null;
	}

	@Override
	public Product save(Product entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}
}
