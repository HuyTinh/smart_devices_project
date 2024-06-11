package com.smart_devices.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private String code;

    private String message;
}