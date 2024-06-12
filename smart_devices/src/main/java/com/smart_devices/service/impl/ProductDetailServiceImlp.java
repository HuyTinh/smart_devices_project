package com.smart_devices.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.smart_devices.dto.RevenueProductDTO;
import com.smart_devices.model.Product;
import com.smart_devices.model.ProductDetail;
import com.smart_devices.repository.ProductDetailRespository;
import com.smart_devices.service.ProductDetailService;

@Service
public class ProductDetailServiceImlp implements ProductDetailService {

	@Autowired
	ProductDetailRespository productDetailRespository;
	
	@Override
	public List<ProductDetail> findByProduct(Product product, Sort sort) {
		// TODO Auto-generated method stub
		return productDetailRespository.findByProduct(product, sort);
	}

	@Override
	public ProductDetail findByTitle(String title) {
		// TODO Auto-generated method stub
		return productDetailRespository.findByTitle(title).get();
	}

	@Override
	public ProductDetail findById(int id) {
		// TODO Auto-generated method stub
		return productDetailRespository.findById(id).get();
	}

	@Override
	public List<ProductDetail> findAll() {
		return productDetailRespository.findAll();
	}

	@Override
	public Page<ProductDetail> findAllPage(Pageable pageable) {
		return productDetailRespository.findAll(pageable);
	}
	@Override
	public void saveAll(List<ProductDetail> entities) {
		 productDetailRespository.saveAll(entities);
	}

	@Override
	public ProductDetail save(ProductDetail entity) {
		return productDetailRespository.save(entity);
	}

	@Override
	public void deleteById(Integer id) {
		productDetailRespository.deleteById(id);
	}

	@Override
	public void deleteByProductDetailId(Integer id) {
		productDetailRespository.deleteByProductDetailId(id);
	}

	@Override
	public Page<ProductDetail> searchProductDetails(String title, Double minPrice, Double maxPrice, Integer minStock,
			Integer maxStock, Pageable pageable) {
		return productDetailRespository.searchProductDetails(title, minPrice, maxPrice, minStock, maxStock, pageable);
	}

	@Override
	public List<ProductDetail> findAllById(List<Integer> ids) {
		return productDetailRespository.findAllById(ids);
	}

	@Override
	public List<RevenueProductDTO> findDailyRevenue(Integer productDetailId, Date startDate, Date endDate) {
		return productDetailRespository.findDailyRevenue(productDetailId, startDate, endDate);
	}

	@Override
	public List<RevenueProductDTO> findTotalRevenueByDate(Integer productDetailId) {
		return productDetailRespository.findTotalRevenueByDate(productDetailId);
	}


	


	
	

}
