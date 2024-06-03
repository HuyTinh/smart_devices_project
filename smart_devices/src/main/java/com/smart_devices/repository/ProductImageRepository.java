package com.smart_devices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart_devices.model.ProductImage;

import jakarta.transaction.Transactional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
	  @Query("SELECT pi.imagePath FROM ProductImage pi WHERE pi.productDetail.id = :productDetailId")
	    List<String> findImagePathsByProductDetailId(@Param("productDetailId") Integer productDetailId);
	  
}
