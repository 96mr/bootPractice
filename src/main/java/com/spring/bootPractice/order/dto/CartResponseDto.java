package com.spring.bootPractice.order.dto;

import com.spring.bootPractice.order.entity.Cart;
import com.spring.bootPractice.product.entity.Product;

import lombok.Getter;

@Getter
public class CartResponseDto {
	private int id;
	private Product productId;
	private String memberId;
	private int count;
	
	public CartResponseDto(Product productId, int count) {
		this.productId = productId;
		this.count = count;
	}
	
	public CartResponseDto(Cart cart) {
		this.id = cart.getId();
		this.productId = cart.getProductId();
		this.memberId = cart.getMemberId().getId();
		this.count = cart.getCount();
	}

	public void setCount(int count) {
		this.count = count;
	}
}
