package com.spring.bootPractice.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.bootPractice.order.dto.OrderDetailResponseDto;
import com.spring.bootPractice.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private OrderRepository orderRepository;
	/*
	public OrderResponseDto save(Map<String, Object> map) {
		
		return new OrderResponseDto();
		//orderRepository.save();
	}
	
	public List<OrderDetailResponseDto> list(int id){
		
	}*/
}
