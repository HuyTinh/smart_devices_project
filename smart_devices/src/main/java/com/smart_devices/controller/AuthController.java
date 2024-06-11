package com.smart_devices.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart_devices.dto.UserSignInDto;
import com.smart_devices.dto.UserSignUpDto;
import com.smart_devices.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	HttpServletRequest request;

	@Autowired
	UserService userService;
	
	@GetMapping("/sign-in")
	public String showLoginForm() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "Component/SignInForm";
		}
		return "redirect:/";
	}

	@PostMapping("/sign-in")
	public String doLoginForm(@Valid UserSignInDto userSignInDto, Errors result, Model model) throws ServletException {
		if (result.hasErrors()) {
			model.addAttribute("userSignIn", userSignInDto);
			model.addAttribute("errorMessageMapSignIn", result.getFieldErrors().stream().collect(Collectors.groupingBy(FieldError::getField)));
			return "Component/SignInForm";
		}

		request.login(userSignInDto.getEmail(), userSignInDto.getPassword());
		return "redirect:/";
	}

	@GetMapping("/sign-up")
	public String showRegisterForm() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "Component/SignUpForm";
		}
		return "redirect:/";
	}
	
	@PostMapping("/sign-up")
	public String doSignUpForm(@Valid UserSignUpDto userSignUpDto, Errors result, Model model) throws ServletException {
		if (result.hasErrors()) {
			for (ObjectError oer : result.getGlobalErrors()) {
				result.rejectValue("matchingPassword", oer.getCode(), oer.getDefaultMessage());
			}
			model.addAttribute("userSignUp", userSignUpDto);
			model.addAttribute("errorMessageMapSignUp", result.getFieldErrors().stream().collect(Collectors.groupingBy(FieldError::getField)));
			return "Component/SignUpForm";
		}

		userService.registerAccount(userSignUpDto);
		return "redirect:/auth/sign-in";
	}

	@ModelAttribute("userSignIn")
	public UserSignInDto getUserSignInDto() {
		return UserSignInDto.builder().build();
	}
	
	@ModelAttribute("userSignUp")
	public UserSignUpDto getUserSignUpDto() {
		return UserSignUpDto.builder().build();
	}

}
