package com.spring.bootPractice.member.dto;

import com.spring.bootPractice.member.entity.Address;
import com.spring.bootPractice.member.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressResponseDto {
	private int id;
	private Member memberId;
	private String address1;
	private String address2;
	private String address3;
	
	public AddressResponseDto(Address address) {
		this.id = address.getId();
		this.memberId = address.getMemberId();
		this.address1 = address.getAddress1();
		this.address2 = address.getAddress2();
		this.address3 = address.getAddress3();
	}
}
