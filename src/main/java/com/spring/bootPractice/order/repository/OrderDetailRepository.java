package com.spring.bootPractice.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.bootPractice.order.entity.Order;
import com.spring.bootPractice.order.entity.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
	List<OrderDetail> findByOrderId(Order orderId);
}
