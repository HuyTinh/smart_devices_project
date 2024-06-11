package com.smart_devices.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.smart_devices.response.BaseResponse;


@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler(BaseException.class)
	public ResponseEntity<BaseResponse> handleBaseException(BaseException e){
		BaseResponse reponse = BaseResponse.builder()
				.code(e.getCode())
				.message(e.getLocalizedMessage())
				.build();
		return ResponseEntity.ok(reponse);
	}

}