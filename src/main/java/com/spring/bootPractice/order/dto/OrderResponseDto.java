package com.spring.bootPractice.order.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.spring.bootPractice.order.entity.Order;

import lombok.Getter;

@Getter
public class OrderResponseDto {
	private String id;
	private String memberId;
	private String receiverName;
	private String receiverPhone;
	private Date orderDate;
	private String address1;
	private String address2;
	private String address3;
	private List<OrderDetailResponseDto> orderDetail;
	
	public OrderResponseDto(Order order) {
		this.id = order.getId();
		this.memberId = order.getMemberId();
		this.receiverName = order.getReceiverName();
		this.receiverPhone = order.getReceiverPhone();
		this.orderDate = order.getOrderDate();
		this.address1 = order.getAddress1();
		this.address2 = order.getAddress2();
		this.address3 = order.getAddress3();
		this.orderDetail = order.getOrderDetail().stream()
								.map(OrderDetailResponseDto::new)
								.collect(Collectors.toList());
	}
}
