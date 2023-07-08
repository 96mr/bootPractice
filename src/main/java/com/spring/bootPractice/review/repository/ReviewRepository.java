package com.spring.bootPractice.review.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.order.entity.OrderDetail;
import com.spring.bootPractice.product.entity.Product;
import com.spring.bootPractice.review.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
	public Optional<Review> findByOrderId(int orderId);
	public Optional<Review> findByOrderDetail(OrderDetail orderId);
	public List<Review> findByProductIdOrderByRegdateDesc(Product productId);
	public List<Review> findByMemberIdOrderByRegdateDesc(Member memberId);	
}
