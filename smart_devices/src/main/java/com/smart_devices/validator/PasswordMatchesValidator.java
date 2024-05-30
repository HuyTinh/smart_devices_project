package com.smart_devices.validator;

import com.smart_devices.dto.UserSignUpDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		System.out.println(value.toString());
		UserSignUpDto user = (UserSignUpDto) value;
		return user.getPassword().equals(user.getMatchingPassword());
	}

}
