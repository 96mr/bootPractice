package com.spring.bootPractice.member.dto;

import javax.validation.constraints.NotBlank;

import com.spring.bootPractice.member.entity.Address;
import com.spring.bootPractice.member.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDto {
	private int id;
	private Member memberId;
	@NotBlank
	private String address1;
	@NotBlank
	private String address2;
	private String address3;
	
	public Address toEntity() {
		return Address.builder()
				.memberId(memberId)
				.address1(address1)
				.address2(address2)
				.address3(address3)
				.build();
	}
}
