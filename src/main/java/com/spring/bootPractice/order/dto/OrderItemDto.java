package com.spring.bootPractice.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDto {
	private int productId;
	private int count;
	private int num;
	private String name;
	private int price;
	private String thumbnail;
	private Integer totalPrice;
	private String status;
	
	@Builder
	public OrderItemDto(int productId, int count, String name, String thumbnail, Integer totalPrice, String status) {
		this.productId = productId;
		this.count = count;
		this.name = name;
		this.thumbnail = thumbnail;
		this.totalPrice = totalPrice;
		this.status = status;
	}
}
