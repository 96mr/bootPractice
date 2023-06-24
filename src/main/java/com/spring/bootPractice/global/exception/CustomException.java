package com.spring.bootPractice.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private ErrorCode errorCode;
	private String message;
	
	public CustomException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.message = errorCode.getMessage();
	}
}
