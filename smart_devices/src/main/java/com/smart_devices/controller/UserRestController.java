package com.smart_devices.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart_devices.dto.OrderDetailDto;
import com.smart_devices.mapper.OrderDetailMapper;
import com.smart_devices.model.MailSender;
import com.smart_devices.model.User;
import com.smart_devices.service.MailService;
import com.smart_devices.service.OrderService;
import com.smart_devices.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
	@Autowired
	private UserService userService; 
	
	@Autowired 
	private OrderService orderService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private OrderDetailMapper orderDetailMapper; 
	
	@GetMapping("/order-history/{orderId}")
	public ResponseEntity<List<OrderDetailDto>> getOrderHistory(@PathVariable("orderId") int orderId) {
		return ResponseEntity.ok(orderService.findById(orderId).getOrderDetails().stream().map(orderDetailMapper::toDto).toList());
	}
	
	@GetMapping("/profile/send-mail")
	public ResponseEntity<String> sendMail() {		
		int otp = otpGenerator();
        MailSender mailSender = MailSender.builder()
                .to(userService.getCurrentUser().getEmail())
                .otp(String.valueOf(otp))
                .subject("Verify OTP")
                .content("This is the OTP for your Forgot Password request: " + otp).build();

        mailService.sendMail(mailSender);
		return ResponseEntity.ok(otpGenerator().toString());
	}
	
	@GetMapping("/profile/change-password/{newPassword}")
	public void changePassowrd(@PathVariable String newPassword) {		
		User user = userService.getCurrentUser();
		user.setPassword(newPassword);
		userService.save(user);
	}
	
	private Integer otpGenerator() {
        // code here
        Random random = new Random();
        return random.nextInt(100_000,999_999);
    }
}
