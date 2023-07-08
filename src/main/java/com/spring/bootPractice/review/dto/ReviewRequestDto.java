package com.spring.bootPractice.review.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.order.entity.OrderDetail;
import com.spring.bootPractice.product.entity.Product;
import com.spring.bootPractice.review.entity.Review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRequestDto {
	private int orderId;
	private OrderDetail orderDetail;
	@NotNull
	private String content;
	private int rating;
	private Product productId;
	private Member memberId;
	private Date regdate;
	
	public Review toEntity() {
		return Review.builder()
					.orderId(orderId)
					.content(content)
					.rating(rating)
					.productId(productId)
					.memberId(memberId)
					.build();
	}
}
