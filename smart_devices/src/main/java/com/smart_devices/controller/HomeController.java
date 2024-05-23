package com.smart_devices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart_devices.service.UserService;

@Controller 
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	UserService userService;
	
	@GetMapping(value = {"/home","/"})
	public String showHomePage() {
		return "page/HomePage";
	}
	
}
