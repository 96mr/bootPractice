package com.spring.bootPractice.order.dto;

import java.util.Date;

import com.spring.bootPractice.order.entity.Order;

import lombok.Getter;

@Getter
public class OrderResponseDto {
	private String id;
	private String memberId;
	private String receiverName;
	private String receiverPhone;
	private Date order_date;
	private String address1;
	private String address2;
	private String address3;
	
	public OrderResponseDto(Order order) {
		this.id = order.getId();
		this.memberId = order.getMemberId();
		this.receiverName = order.getReceiverName();
		this.receiverPhone = order.getReceiverPhone();
		this.order_date = order.getOrder_date();
		this.address1 = order.getAddress1();
		this.address2 = order.getAddress2();
		this.address3 = order.getAddress3();
	}
}
