package com.spring.bootPractice.review.dto;

import java.util.Date;

import com.spring.bootPractice.member.dto.MemberResponseDto;
import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.order.dto.OrderDetailResponseDto;
import com.spring.bootPractice.product.dto.ProductResponseDto;
import com.spring.bootPractice.product.entity.Product;
import com.spring.bootPractice.review.entity.Review;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {
	private int orderId;
	private OrderDetailResponseDto orderDetail;
	private String content;
	private int rating;
	private ProductResponseDto productId;
	private MemberResponseDto memberId;
	private Date regdate;
	
	public ReviewResponseDto(Review review) {
		this.orderId = review.getOrderId();
		this.content = review.getContent();
		this.rating = review.getRating();
		this.productId = new ProductResponseDto(review.getProductId());
		this.memberId = new MemberResponseDto(review.getMemberId());
		this.orderDetail = new OrderDetailResponseDto(review.getOrderDetail());
		this.regdate = review.getRegdate();
	}
}
