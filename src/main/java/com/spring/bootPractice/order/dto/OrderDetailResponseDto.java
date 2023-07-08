package com.spring.bootPractice.order.dto;

import com.spring.bootPractice.order.entity.OrderDetail;
import com.spring.bootPractice.order.entity.Status;
import com.spring.bootPractice.product.entity.Product;
import com.spring.bootPractice.review.entity.Review;

import lombok.Getter;

@Getter
public class OrderDetailResponseDto {
	private int id;
	private String orderId;
	private Product productId;
	private int count;
	private int price;
	private Status status;
	private Review review;
	
	public OrderDetailResponseDto(OrderDetail orderDetail) {
		this.id = orderDetail.getId();
		this.orderId = orderDetail.getOrderId().getId();
		this.productId = orderDetail.getProductId();
		this.count = orderDetail.getCount();
		this.price = orderDetail.getPrice();
		this.status = orderDetail.getStatus();	
		this.review = orderDetail.getReview();
	}
	
}
