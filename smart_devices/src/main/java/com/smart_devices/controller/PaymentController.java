package com.smart_devices.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart_devices.enums.OrderStatus;
import com.smart_devices.enums.PaymentMethod;
import com.smart_devices.model.Cart;
import com.smart_devices.model.Order;
import com.smart_devices.model.OrderDetail;
import com.smart_devices.payment.PaymentService;
import com.smart_devices.service.CartService;
import com.smart_devices.service.OrderService;
import com.smart_devices.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	OrderService orderService;

	@Autowired
	CartService cartService;
	
	@Autowired
	UserService userService;

	@PostMapping("/{payBy}")
	public void getMethodName(@PathVariable("payBy") String payBy, @RequestParam("shipping-address") String shippingAddress, @RequestParam("amount") double totalPrice, HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		List<Cart> carts = cartService.findByCustomer(userService.getCurrentUser());
		if (carts.size() > 0) {
			PaymentMethod pm = null;
			if (payBy.contains("vnpay")) {
				pm = PaymentMethod.VNPAY;
				PaymentService.VNPay(req, resp);
			} else if (payBy.contains("paypal")) {
				pm = PaymentMethod.PAYPAL;
				PaymentService.PayPal(req, resp);
			} else if (payBy.contains("cash_on_delivery")) {
				pm = PaymentMethod.CASH_ON_DELIVERY;
				resp.sendRedirect("/cart");
			}
			Order order = Order.builder().user(carts.get(0).getUser()).paymentMethod(pm).totalPrice(totalPrice).shippingAddress(shippingAddress).build();
			List<OrderDetail> orderDetails = carts.stream()
					.map(cart -> OrderDetail.builder().order(order).productDetail(cart.getProductDetail())
							.quantity(cart.getQuantity()).price(cart.getProductDetail().getPrice()).build())
					.collect(Collectors.toList());
			order.setOrderDetails(orderDetails);
			orderService.save(order);
			cartService.clearCart();
		} else {
			resp.sendRedirect("/cart");
		}
	}

}
