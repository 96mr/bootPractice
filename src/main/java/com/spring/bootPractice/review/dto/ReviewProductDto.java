package com.spring.bootPractice.review.dto;

import java.util.Date;

import com.spring.bootPractice.review.entity.Review;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewProductDto {
	private int orderId;
	private String content;
	private int rating;
	private int count;
	private int productId;
	private String productName;
	private String memberName;
	private Date regdate;
	
	public ReviewProductDto(Review review) {
		this.orderId = review.getOrderId();
		this.content = review.getContent();
		this.rating = review.getRating();
		this.productId = review.getProductId().getPid();
		this.productName = review.getProductId().getPname();
		this.memberName = review.getMemberId().getName();
		this.count = review.getOrderDetail().getCount();
		this.regdate = review.getRegdate();
	}
}
