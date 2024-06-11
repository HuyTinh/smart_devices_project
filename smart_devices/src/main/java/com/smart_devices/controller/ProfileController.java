package com.smart_devices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart_devices.model.User;
import com.smart_devices.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	HttpSession session;
	
	@Autowired 
	private UserService userSevice;
	
	@GetMapping
	public String profile() {
		return "page/ProfilePage";
	}
	
	@ModelAttribute("currentUser")
	public User getCurrentUser() {
		return userSevice.getCurrentUser();
	}
	
	@PostMapping 
	public String updateProfile(@ModelAttribute("currentUser") User user){
		userSevice.save(user);
		return "page/ProfilePage";
	}
}
