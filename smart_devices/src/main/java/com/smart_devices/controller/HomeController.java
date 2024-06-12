package com.smart_devices.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
	public String showHomePage(Principal principal, Authentication authentication) {
		if (principal != null) {
			boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
			if (isAdmin) {
				return "redirect:/admin/cat-phone";
			}
			session.setAttribute("user", userService.findByEmail(principal.getName()));
		}
		return "page/HomePage";
	}

}
