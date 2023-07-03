package com.spring.bootPractice.order.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.spring.bootPractice.order.entity.Order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
	private String id;
	private String memberId;
	@NotBlank
	private String receiverName;
	@NotBlank
	private String receiverPhone;
	private Date order_date;
	private String address1;
	private String address2;
	private String address3;
	
	@Builder
	public OrderRequestDto(String id, String memberId, String receiverName, String receiverPhone,
						String address1, String address2, String address3) {
		this.id = id;
		this.memberId = memberId;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
	}
	
	public Order toEntity() {
		return Order.builder()
				.id(id)
				.memberId(memberId)
				.receiverName(receiverName)
				.receiverPhone(receiverPhone)
				.address1(address1)
				.address2(address2)
				.address3(address3)
				.build();
	}
}
