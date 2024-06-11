package com.smart_devices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_devices.model.Cart;
import com.smart_devices.model.ProductDetail;
import com.smart_devices.model.User;
import com.smart_devices.repository.CartRepository;
import com.smart_devices.service.CartService;
import com.smart_devices.service.UserService;

@Service
public class CartServiceImlp implements CartService {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	UserService userService;

	@Override
	public List<Cart> findByCustomer(User user) {
		// TODO Auto-generated method stub
		return cartRepository.findByUser(user);
	}

	@Override
	public Double getAmount() {
		// TODO Auto-generated method stub
		User user = userService.getCurrentUser();
		return user.getCarts().stream().mapToDouble((c) -> c.getQuantity() * c.getProductDetail().getPrice()).sum();
	}

	@Override
	public void addToCart(ProductDetail productDetail) {
		// TODO Auto-generated method stub
		User user = userService.getCurrentUser();
		List<Cart> carts = user.getCarts();
		carts.stream().filter((pd) -> pd.getProductDetail().getId() == productDetail.getId()).findFirst().ifPresentOrElse((value) -> {
			value.setQuantity(value.getQuantity() + 1);
		}, () -> carts.add(Cart.builder().productDetail(productDetail).user(user).build()));

		userService.save(user);
	}

	@Override
	public void removeFromCart(int productDetailId) {
		// TODO Auto-generated method stub
		User user = userService.getCurrentUser();
		List<Cart> carts = user.getCarts();
		carts.stream().filter((pd) -> pd.getProductDetail().getId() == productDetailId).findFirst().ifPresent(carts::remove);
		userService.save(user);
	}

	@Override
	public List<Cart> findAll() {
		// TODO Auto-generated method stub
		return cartRepository.findAll();
	}
	
	@Override
	public void clearCart() {
		// TODO Auto-generated method stub
		User user = userService.getCurrentUser();
		List<Cart> carts = user.getCarts();
		carts.clear();
		userService.save(user);
	}
}
