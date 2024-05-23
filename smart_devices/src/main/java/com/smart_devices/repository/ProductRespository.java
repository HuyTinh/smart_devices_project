package com.smart_devices.repository;

import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart_devices.model.Product;

@Repository
public interface ProductRespository extends JpaRepository<Product, Integer> {
	Optional<Product> findByLineOfProduct(String lineOfProduct);
	Product findByLineOfProduct(String lineOfProduct, Sort sort);
}
