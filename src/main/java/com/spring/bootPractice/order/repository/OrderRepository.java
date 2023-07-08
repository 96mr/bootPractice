package com.spring.bootPractice.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.bootPractice.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
	public Optional<Order> findById(String id);
	public List<Order> findByMemberIdOrderByOrderDateDesc(String memberId);
}
