package com.smart_devices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart_devices.model.Product;
import com.smart_devices.model.ProductDetail;

@Repository
public interface ProductDetailRespository extends JpaRepository<ProductDetail, Integer> {
	
	Optional<ProductDetail> findById(int id);
	
	Optional<ProductDetail> findByTitle(String title);
	
	List<ProductDetail> findByProduct(Product product, Sort sort);
}
