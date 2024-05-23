package com.smart_devices.dto;

import groovy.transform.builder.Builder;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Builder
public class UserLoginDto{
	@NotBlank(message = "Email is empty")
	String email;
	@NotBlank(message = "Password is empty")
	String password;
}
