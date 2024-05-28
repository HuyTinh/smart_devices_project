package com.smart_devices.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("cat-phone/admin")
public class DashBoardController {
	@GetMapping
	public String showDashBoard() {
		return "dashBoard";
	}
	
	@GetMapping("oders")
	public String oders() {
		return "page/oders";
	}
	@GetMapping("customer")
	public String customer() {
		return "page/customer";
	}
	@GetMapping("report")
	public String report() {
		return "page/report";
	}
}
