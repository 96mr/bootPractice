package com.spring.bootPractice.order.dto;

import com.spring.bootPractice.order.entity.Order;
import com.spring.bootPractice.order.entity.OrderDetail;
import com.spring.bootPractice.order.entity.Status;
import com.spring.bootPractice.product.entity.Product;

import lombok.Getter;

@Getter
public class OrderDetailResponseDto {
	private int id;
	private Order orderId;
	private Product productId;
	private int count;
	private int price;
	private Status status;
	
	public OrderDetailResponseDto(OrderDetail orderDetail) {
		this.orderId = orderDetail.getOrderId();
		this.productId = orderDetail.getProductId();
		this.count = orderDetail.getCount();
		this.price = orderDetail.getPrice();
		this.status = orderDetail.getStatus();				
	}
	
}
