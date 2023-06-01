package com.spring.bootPractice.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bootPractice.member.dto.AddressRequestDto;
import com.spring.bootPractice.member.dto.AddressResponseDto;
import com.spring.bootPractice.member.entity.Address;
import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.member.repository.AddressRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AddressService {
	
	private final AddressRepository addressRepository;

	public AddressResponseDto save(AddressRequestDto dto, Member member) {
		dto.setMemberId(member);
		Address address = addressRepository.save(dto.toEntity());
		return new AddressResponseDto(address);
	}
	
	@Transactional
	public AddressResponseDto update(AddressRequestDto dto, Member member) {
		Address address = addressRepository.findByMemberId(member)
									.orElseThrow(()-> new IllegalStateException("주소가 존재하지 않습니다."));
		address.update(dto.getAddress1(), dto.getAddress2(), dto.getAddress3());
		return new AddressResponseDto(address);
	}
	
	public AddressResponseDto getAddressById(Member member) {
		Optional<Address> address = addressRepository.findByMemberId(member);
		if(address.isPresent()) {
			return new AddressResponseDto(address.get());
		}else {
			return new AddressResponseDto();
		}
	}
	
}
