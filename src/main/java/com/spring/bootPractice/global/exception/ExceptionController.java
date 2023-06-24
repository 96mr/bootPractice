package com.spring.bootPractice.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> exception(CustomException e){
		log.error("RuntimeException : {}", e.getErrorCode().getMessage());
		return ResponseEntity
						.status(e.getErrorCode().getStatus().value())
						.body(new ErrorResponse(e.getErrorCode()));
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<?> exception(NoHandlerFoundException e){
		log.error("Exception : {}", e.getMessage());
		return ResponseEntity
						.status(ErrorCode.NO_HANDLER_FOUND.getStatus().value())
						.body(new ErrorResponse(ErrorCode.NO_HANDLER_FOUND));
	}

	@ExceptionHandler(Exception.class) 
	public ResponseEntity<ErrorResponse> exception(Exception e){ 
		log.error("Excetpion : {}", e.getMessage());
		return ResponseEntity
			 		.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
			 		.body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
	}
}
