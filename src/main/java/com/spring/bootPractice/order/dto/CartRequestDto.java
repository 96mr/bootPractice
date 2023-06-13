package com.spring.bootPractice.order.dto;

import javax.validation.constraints.NotNull;

import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.order.entity.Cart;
import com.spring.bootPractice.product.entity.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequestDto {
	private int id;
	@NotNull
	private Product productId;
	private Member memberId;
	private int count;
	
	@Builder
	public CartRequestDto(Product productId, Member memberId, int count) {
		this.productId = productId;
		this.memberId = memberId;
		this.count = count;
	}
	
	public Cart toEntity() {
		return Cart.builder()
				.id(id)
				.productId(productId)
				.memberId(memberId)
				.count(count)
				.build();
	}
}
