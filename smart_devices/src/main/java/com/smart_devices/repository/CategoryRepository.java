package com.smart_devices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_devices.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
