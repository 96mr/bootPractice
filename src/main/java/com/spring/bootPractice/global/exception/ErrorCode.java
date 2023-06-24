package com.spring.bootPractice.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다"),
	PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다"),
	CART_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니에 존재하지 않습니다"),
	DUPLICATED_USER_ID(HttpStatus.CONFLICT,"중복된 아이디입니다"),
	DUPLICATED_USER_EMAIL(HttpStatus.CONFLICT,"중복된 이메일입니다"),
	
	BAD_REQUEST(HttpStatus.BAD_REQUEST,"잘못된 요청입니다"),
	NO_HANDLER_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 페이지입니다"),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED,"허용되지 않은 메서드입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"내부 서버 오류입니다");
	
	private final HttpStatus status;
	private final String message;
}
