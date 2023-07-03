package com.spring.bootPractice.product.entity;

import lombok.Getter;

@Getter
public enum ProductStatus {
	FOR_SALE("판매중"),
	ON_SALE("할인중"),
	SOLD_OUT("품절"),
	DISCONTINUED("판매중단");

	String status;
	
	ProductStatus(String status){
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}
