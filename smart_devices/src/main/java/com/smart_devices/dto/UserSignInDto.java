package com.smart_devices.dto;

import com.smart_devices.validator.ValidEmail;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserSignInDto{
	
	@ValidEmail(message = "Email format must be (somthing@somthing.xxx)")
	@NotBlank(message = "Email is require not to be empty")
	String email;
	
	@NotBlank(message = "Password is require not to be empty")
	String password;

}
