package com.spring.bootPractice.order.entity;

public enum Status {
	Processed("상품 준비중"),
	Shipped("출고 완료"),
	Delivery("배송중"),
	Delivered("배송완료");
	
	private String status;
	
	Status(String status){
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}
