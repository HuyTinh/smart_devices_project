package com.smart_devices.dto;

import com.smart_devices.validator.PasswordMatches;
import com.smart_devices.validator.ValidEmail;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@PasswordMatches
public class UserSignUpDto {
	@NotBlank(message = "First Name is require not to be empty")
	private String firstName;
    
	@NotBlank(message = "Last Name is require not to be empty")
	private String lastName;
    
	@NotBlank(message = "Password is require not to be empty")
    private String password;
	private String matchingPassword;
    
    @NotBlank(message = "Email is require not to be empty")
    @ValidEmail
    private String email;
    
    @Builder.Default
    private String role = "USER";
}
