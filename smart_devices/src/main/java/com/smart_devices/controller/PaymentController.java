package com.smart_devices.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.smart_devices.payment.PaymentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/payment")
public class PaymentController {
	
	@GetMapping("/{payBy}")
	public void getMethodName(@PathVariable("payBy") String payBy, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if(payBy.contains("vnpay")) {
			PaymentService.VNPay(req, resp);
		} else if (payBy.contains("paypal")){
			PaymentService.PayPal(req, resp);
		}
	}
	
}
