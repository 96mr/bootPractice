package com.spring.bootPractice.order.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderPageDto {
	private String orderId;
	@NotBlank
	private String receiverName;
	@NotBlank
	private String receiverPhone;
	@NotBlank
	private String postcode;
	@NotBlank
	private String address;
	@NotBlank
	private String detailAddress;
	@NotNull
	private List<OrderItemDto> items;
	
	@Builder
	public OrderPageDto(String receiverName, String receiverPhone, String postcode, 
			String address, String detailAddress, List<OrderItemDto> items) {
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.postcode = postcode;
		this.address = address;
		this.detailAddress = detailAddress;
		this.items = items;
	}
	
}
