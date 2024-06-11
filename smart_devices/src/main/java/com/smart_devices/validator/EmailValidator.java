package com.smart_devices.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	private Pattern pattern;

	private Matcher matcher;

	private static  final String EMAIL_PATTERN = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

	
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return validateEmail(email);
	}

	private boolean validateEmail(String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		
		return matcher.matches();
	}
}
