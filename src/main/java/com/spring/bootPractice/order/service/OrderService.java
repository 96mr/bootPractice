package com.spring.bootPractice.order.service;

import org.springframework.stereotype.Service;

import com.spring.bootPractice.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private OrderRepository orderRepository;
}
