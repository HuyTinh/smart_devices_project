package com.smart_devices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_devices.model.Cart;
import com.smart_devices.model.ProductDetail;
import com.smart_devices.model.User;
import com.smart_devices.repository.CartRepository;
import com.smart_devices.service.CartService;

@Service
public class CartServiceImlp implements CartService {

	private List<Cart> list;

	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	UserServiceImlp userServiceImlp;
	
	@Override
	public List<Cart> findByCustomer(User user) {
		// TODO Auto-generated method stub
		return cartRepository.findByUser(user);
	}

	@Override
	public Double getAmount() {
		User user = userServiceImlp.findById(2);
		// TODO Auto-generated method stub
		return 	user.getCarts().stream().mapToDouble((c) -> c.getQuantity() * c.getProductDetail().getPrice()).sum();
	}

	@Override
	public void delete(Cart cart) {
		// TODO Auto-generated method stub
		cartRepository.delete(cart);;
	}

	@Override
	public void addToCart(ProductDetail productDetail) {
		// TODO Auto-generated method stub
		User user = userServiceImlp.findById(2);
		List<Cart> carts = user.getCarts();
		carts.stream().filter((pd) -> pd.equals(productDetail)).findFirst().ifPresentOrElse((value) -> {
			value.setQuantity(value.getQuantity() + 1);
		}, () -> carts.add(Cart.builder().productDetail(productDetail).user(user).build()));

		userServiceImlp.save(user);
	}

	@Override
	public void removeFromCart(ProductDetail productDetail) {
		// TODO Auto-generated method stub
		User user = userServiceImlp.findById(2);
		List<Cart> carts = user.getCarts();
		carts.stream().filter((pd) -> pd.equals(productDetail))
			.findFirst()
			.ifPresent(carts::remove);	

	}
	
	
	
	
}
