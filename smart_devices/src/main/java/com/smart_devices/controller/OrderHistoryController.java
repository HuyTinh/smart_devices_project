package com.smart_devices.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart_devices.model.User;
import com.smart_devices.model.Order;
import com.smart_devices.service.OrderService;
import com.smart_devices.service.UserService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
@RequestMapping("/order-history")
public class OrderHistoryController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	UserService userService; 
	
	@GetMapping
	public String showOrderHistory() {
		return "Page/OrderHistoryPage";
	}
	
	@ModelAttribute("orderHistory")
	public List<Order> getOrderHistory(){
		return userService.getCurrentUser().getOrders();
	}

	
}
