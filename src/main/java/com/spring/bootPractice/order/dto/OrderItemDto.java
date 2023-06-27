package com.spring.bootPractice.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
	private int productId;
	private int count;
	private int num;
	private String name;
	private int price;
	private int totalPrice;
}
