package com.smart_devices.service;

import java.util.List;

import com.smart_devices.model.Cart;
import com.smart_devices.model.ProductDetail;
import com.smart_devices.model.User;

public interface CartService {
	
	List<Cart> findAll();
	
	List<Cart> findByCustomer(User user);
	
	Double getAmount();
	
	void addToCart(ProductDetail productDetail);

	void removeFromCart(int productDetailId);
	
	void clearCart();
}
