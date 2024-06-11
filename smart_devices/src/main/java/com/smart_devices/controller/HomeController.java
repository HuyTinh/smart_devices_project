package com.smart_devices.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart_devices.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	UserService userService;

	@Autowired
	HttpSession session;

	@GetMapping(value = { "/home", "/" })
	public String showHomePage(Principal principal) {
		if (principal != null) {
			session.setAttribute("user", userService.findByEmail(principal.getName()));
		}
		return "page/HomePage";
	}

}
