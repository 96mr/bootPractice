package com.spring.bootPractice.order.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.spring.bootPractice.member.entity.MemberDetail;
import com.spring.bootPractice.order.dto.CartResponseDto;
import com.spring.bootPractice.order.dto.OrderItemDto;
import com.spring.bootPractice.order.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CartController {
	
	private final CartService cartService;
	
	@SuppressWarnings("unchecked")
	@GetMapping(value="/api/cart")
	public ResponseEntity<JsonArray> list(HttpSession session, @AuthenticationPrincipal MemberDetail memberDetail) {
		List<CartResponseDto> list = (memberDetail == null) ?
				(List<CartResponseDto>) session.getAttribute("cart") 
				:cartService.list(memberDetail.getMember());
		if(list == null || list.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		JsonArray array = cartService.parseListJson(list);
		return ResponseEntity.ok()
							.body(array);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/api/cart")
	public ResponseEntity<Object> save(@RequestBody OrderItemDto dto,
						@AuthenticationPrincipal MemberDetail memberDetail,
						HttpSession session) {
		if(memberDetail == null) {
			List<CartResponseDto> list = (List<CartResponseDto>) session.getAttribute("cart");
			list = cartService.save(list, dto);
			session.setAttribute("cart", list);
		}else {
			cartService.save(memberDetail.getMember(), dto);
		}
		return ResponseEntity.ok().build();
	}
	
	@SuppressWarnings("unchecked")
	@PutMapping(value="/api/cart")
	public ResponseEntity<Object> update(@RequestBody OrderItemDto dto, HttpSession session,
						@AuthenticationPrincipal MemberDetail memberDetail) {
		if((memberDetail == null)) {
			List<CartResponseDto> list = (List<CartResponseDto>) session.getAttribute("cart");
			list = cartService.update(list, dto);
			session.setAttribute("cart", list);
		}else {
			cartService.update(dto);
		}
		return ResponseEntity.ok().build();
	}
	
	@SuppressWarnings("unchecked")
	@DeleteMapping(value="/api/cart")
	public ResponseEntity<Object> delete(@RequestBody OrderItemDto dto, HttpSession session) {
		if(dto.getNum() == 0) {
			List<CartResponseDto> list = (List<CartResponseDto>) session.getAttribute("cart");
			cartService.delete(list, dto.getProductId());
		}else {
			cartService.delete(dto.getNum());
		}
		return ResponseEntity.ok().build();
	}

}
