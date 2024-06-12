package com.smart_devices.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.smart_devices.dto.RevenueProductDTO;
import com.smart_devices.model.Product;
import com.smart_devices.model.ProductDetail;

public interface ProductDetailService {
	
	ProductDetail findById(int id);
	
	ProductDetail findByTitle(String title);
	
	List<ProductDetail> findByProduct(Product product, Sort sort);

	List<ProductDetail> findAll();
	
	void saveAll(List<ProductDetail> productDetails);

	Page<ProductDetail> findAllPage(Pageable pageable);

	ProductDetail save(ProductDetail entity);
	void deleteById(Integer id);
	void deleteByProductDetailId(Integer id);
	Page<ProductDetail> searchProductDetails(String title, Double minPrice, Double maxPrice, Integer minStock, Integer maxStock, Pageable pageable);

	List<ProductDetail> findAllById(List<Integer> ids);


	List<RevenueProductDTO> findDailyRevenue(Integer productDetailId, Date startDate, Date endDate);

	List<RevenueProductDTO> findTotalRevenueByDate(Integer productDetailId);

}	
