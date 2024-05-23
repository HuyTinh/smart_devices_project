package com.smart_devices.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_devices.model.Cart;
import com.smart_devices.model.User;

import java.util.List;


public interface CartRepository extends JpaRepository<Cart, Integer>  {
	List<Cart> findByUser(User user);
}
