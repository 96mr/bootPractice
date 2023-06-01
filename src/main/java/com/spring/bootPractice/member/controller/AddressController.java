package com.spring.bootPractice.member.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.spring.bootPractice.member.dto.AddressRequestDto;
import com.spring.bootPractice.member.dto.AddressResponseDto;
import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.member.entity.MemberDetail;
import com.spring.bootPractice.member.service.AddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AddressController {

	private final AddressService addressService;
	
	@GetMapping(value="/address")
	public JsonObject getAddress(@AuthenticationPrincipal MemberDetail memberDetail, Model model) {
		JsonObject json = new JsonObject();
		Member member = memberDetail.getMember();
		AddressResponseDto result = addressService.getAddressById(member);
		json.addProperty("postcode", result.getAddress1());
		json.addProperty("address", result.getAddress2());
		json.addProperty("detail_address", result.getAddress3());
		return json;
	}
	
	@PostMapping(value="/address")
	public JsonObject insertAddress(@RequestBody AddressRequestDto addressDto,
									@AuthenticationPrincipal MemberDetail memberDetail, Model model) {
		JsonObject json = new JsonObject();
		Member member = memberDetail.getMember();
		AddressResponseDto result = addressService.save(addressDto, member);
		json.addProperty("postcode", result.getAddress1());
		json.addProperty("address", result.getAddress2());
		json.addProperty("detail_address", result.getAddress3());
		return json;
	}
	
	@PutMapping(value="/address")
	public JsonObject updateAddress(@RequestBody AddressRequestDto addressDto,
									@AuthenticationPrincipal MemberDetail memberDetail, Model model) {
		JsonObject json = new JsonObject();
		Member member = memberDetail.getMember();
		AddressResponseDto result = addressService.update(addressDto, member);
		json.addProperty("postcode", result.getAddress1());
		json.addProperty("address", result.getAddress2());
		json.addProperty("detail_address", result.getAddress3());
		return json;
	}
}
