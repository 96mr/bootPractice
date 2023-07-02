package com.spring.bootPractice.order.dto;

import com.spring.bootPractice.order.entity.Order;
import com.spring.bootPractice.order.entity.OrderDetail;
import com.spring.bootPractice.order.entity.Status;
import com.spring.bootPractice.product.entity.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailRequestDto {
	private int id;
	private Order orderId;
	private Product productId;
	private int count;
	private int price;
	private Status status;
	
	@Builder
	public OrderDetailRequestDto(Order orderId, Product productId, int count, int price, Status status) {
		this.orderId = orderId;
		this.productId = productId;
		this.count = count;
		this.price = price;
		this.status = status;
	}
	
	public OrderDetail toEntity() {
		return OrderDetail.builder()
				.orderId(orderId)
				.productId(productId)
				.count(count)
				.price(price)
				.status(status)
				.build();
	}
}
