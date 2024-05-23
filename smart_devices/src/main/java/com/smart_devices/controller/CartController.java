package com.smart_devices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart_devices.model.Cart;
import com.smart_devices.service.CartService;
import com.smart_devices.service.UserService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	UserService userService;
	
	@Autowired
	CartService cartService;
	
	@GetMapping
	public String showCart() {
		return "page/CartPage";
	}
	

	@PostMapping("/plus/{id}")
	public String addCart(@PathVariable("id") int id) {
		Cart cart = getCarts().get(id);
		cart.setQuantity(cart.getQuantity() + 1);
		userService.save(cart.getUser());
		return "redirect:/cart";
	}
	
	@PostMapping("/minus/{id}")
	public String minusCart(@PathVariable("id") int id) {
		List<Cart> carts = getCarts();
		Cart cart = carts.get(id);
		int quantity = cart.getQuantity();
		if(-- quantity == 0) {
			carts.remove(carts.indexOf(cart));
		} else {
			cart.setQuantity(quantity);
		}
		userService.save(cart.getUser());
		return "redirect:/cart";
	}
	
	
	@ModelAttribute("carts")
	public List<Cart> getCarts() {
		return userService.findById(2).getCarts();
	}
	
	@ModelAttribute("amount")
	public Double getAmount() {
		return cartService.getAmount();
	}
}
