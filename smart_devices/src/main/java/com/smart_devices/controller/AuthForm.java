package com.smart_devices.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthForm {
	
	@GetMapping("/login")
	public String showLoginForm() {
		return "page/AuthPage";
	}
}
