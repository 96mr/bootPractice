package com.spring.bootPractice.review.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bootPractice.global.exception.CustomException;
import com.spring.bootPractice.global.exception.ErrorCode;
import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.order.entity.Order;
import com.spring.bootPractice.order.entity.OrderDetail;
import com.spring.bootPractice.order.repository.OrderDetailRepository;
import com.spring.bootPractice.order.repository.OrderRepository;
import com.spring.bootPractice.product.entity.Product;
import com.spring.bootPractice.product.repository.ProductRepository;
import com.spring.bootPractice.review.dto.ReviewProductDto;
import com.spring.bootPractice.review.dto.ReviewRequestDto;
import com.spring.bootPractice.review.dto.ReviewResponseDto;
import com.spring.bootPractice.review.entity.Review;
import com.spring.bootPractice.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;

	public List<ReviewProductDto> findByProductId(int productId) {
		Product product = productRepository.findByPid(productId)
				.orElseThrow(()->new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
		List<Review> review= reviewRepository.findByProductIdOrderByRegdateDesc(product);
		return review.stream().map(ReviewProductDto::new).collect(Collectors.toList());
	}

	public List<ReviewResponseDto> findByMemberId(Member memberId) {
		List<Review> review = reviewRepository.findByMemberIdOrderByRegdateDesc(memberId);
		return review.stream().map(ReviewResponseDto::new).collect(Collectors.toList());
	}
	
	public ReviewResponseDto findByOrderId(int orderId) {
		OrderDetail order = orderDetailRepository.findById(orderId)
				.orElseThrow(()->new CustomException(ErrorCode.ORDER_NOT_FOUND));
		Optional<Review> review = reviewRepository.findByOrderDetail(order);
		if(review.isPresent()) {
			return new ReviewResponseDto(review.get());
		}
		return new ReviewResponseDto();
	}
	
	public boolean equalsMemberId(String member, int orderId) {
		OrderDetail orderDetail = orderDetailRepository.findById(orderId)
							.orElseThrow(()->new CustomException(ErrorCode.ORDER_NOT_FOUND));
		Order order = orderRepository.findById(orderDetail.getOrderId().getId())
							.orElseThrow(()->new CustomException(ErrorCode.ORDER_NOT_FOUND));
		if(order.getMemberId().equals(member)){
			return true;
		}else {
			return false;
		}
	}
	
	@Transactional
	public ReviewResponseDto save(ReviewRequestDto requestDto) {
		reviewRepository.findByOrderId(requestDto.getOrderId())
						.orElseThrow(()->new CustomException(ErrorCode.DUPLICATED_REVIEW));		
		OrderDetail order = orderDetailRepository.findById(requestDto.getOrderId())
				.orElseThrow(()->new CustomException(ErrorCode.ORDER_NOT_FOUND));
		requestDto.setProductId(order.getProductId());
		Review review = reviewRepository.save(requestDto.toEntity());
		return new ReviewResponseDto(review);
	}
	
}
