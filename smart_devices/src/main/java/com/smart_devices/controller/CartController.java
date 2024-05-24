package com.smart_devices.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart_devices.model.Cart;
import com.smart_devices.model.Order;
import com.smart_devices.model.OrderDetail;
import com.smart_devices.service.CartService;
import com.smart_devices.service.OrderService;
import com.smart_devices.service.UserService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	UserService userService;

	@Autowired
	CartService cartService;

	@Autowired
	OrderService orderService;

	@GetMapping
	public String showCart(@RequestParam(required = false, name = "paid") String paid) {
		if (paid != null) {
			List<Cart> carts = getCarts();
			Order order = Order.builder().user(carts.get(0).getUser()).shippingAddress("a").build();
			List<OrderDetail> orderDetails = carts.stream()
					.map(cart -> OrderDetail.builder().order(order).productDetail(cart.getProductDetail())
							.quantity(cart.getQuantity()).price(cart.getProductDetail().getPrice()).build())
					.collect(Collectors.toList());
			order.setOrderDetails(orderDetails);
			orderService.save(order);
			cartService.clearCart();
		}
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
		if (--quantity == 0) {
			carts.remove(carts.indexOf(cart));
		} else {
			cart.setQuantity(quantity);
		}
		userService.save(cart.getUser());
		return "redirect:/cart";
	}

	@PostMapping("/remove/{id}")
	public String removeCart(@PathVariable("id") int id) {
		cartService.removeFromCart(id);
		return "redirect:/cart";
	}

	@ModelAttribute("carts")
	public List<Cart> getCarts() {
		return userService.getCartList();
	}

	@ModelAttribute("amount")
	public Double getAmount() {
		return cartService.getAmount();
	}
}
