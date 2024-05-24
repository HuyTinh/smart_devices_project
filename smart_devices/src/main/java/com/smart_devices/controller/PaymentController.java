package com.smart_devices.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart_devices.model.Cart;
import com.smart_devices.payment.PaymentService;
import com.smart_devices.service.CartService;
import com.smart_devices.service.OrderService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	OrderService orderService;

	@Autowired
	CartService cartService;

	@GetMapping("/{payBy}")
	public void getMethodName(@PathVariable("payBy") String payBy, HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		List<Cart> carts = cartService.findAll();
		if (carts.size() > 0) {
			if (payBy.contains("vnpay")) {
				PaymentService.VNPay(req, resp);
			} else if (payBy.contains("paypal")) {
				PaymentService.PayPal(req, resp);
			}
		} else {
			resp.sendRedirect("/cart");
		}
	}

}
