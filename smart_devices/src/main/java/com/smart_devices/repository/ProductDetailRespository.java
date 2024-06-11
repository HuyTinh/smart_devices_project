package com.smart_devices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smart_devices.model.Product;
import com.smart_devices.model.ProductDetail;

import jakarta.transaction.Transactional;

@Repository
public interface ProductDetailRespository extends JpaRepository<ProductDetail, Integer> {

	Optional<ProductDetail> findById(int id);

	Optional<ProductDetail> findByTitle(String title);

	List<ProductDetail> findByProduct(Product product, Sort sort);

	@Modifying
	@Transactional
	@Query("DELETE FROM ProductDetail pd WHERE pd.id = :id")
	void deleteByProductDetailId(@Param("id") Integer id);
	
	@Query("SELECT pd FROM ProductDetail pd WHERE "
	        + "(:title IS NULL OR pd.title LIKE %:title%) AND "
	        + "(:minPrice IS NULL OR pd.price >= :minPrice) AND "
	        + "(:maxPrice IS NULL OR pd.price <= :maxPrice) AND "
	        + "(:minStock IS NULL OR pd.stock >= :minStock) AND "
	        + "(:maxStock IS NULL OR pd.stock <= :maxStock)")
	Page<ProductDetail> searchProductDetails(
	        @Param("title") String title,
	        @Param("minPrice") Double minPrice,
	        @Param("maxPrice") Double maxPrice,
	        @Param("minStock") Integer minStock,
	        @Param("maxStock") Integer maxStock,
	        Pageable pageable);
	
}
