package com.spring.bootPractice.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.bootPractice.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
}
