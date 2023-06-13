package com.spring.bootPractice.order.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.spring.bootPractice.member.entity.MemberDetail;
import com.spring.bootPractice.order.dto.CartResponseDto;
import com.spring.bootPractice.order.service.CartService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {
	
	private final CartService cartService;

	@GetMapping(value="/cart")
	public String cart() {
		return "order/cart";
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping(value="/cart/api")
	public String list(HttpSession session, @AuthenticationPrincipal MemberDetail memberDetail) {
		List<CartResponseDto> list = (memberDetail == null) ?
									(List<CartResponseDto>) session.getAttribute("cart") 
									:cartService.list(memberDetail.getMember());
		JsonArray array = new JsonArray();
		if(list != null) array = cartService.parseListJson(list);
		return array.toString();
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@PostMapping(value = "/cart/api")
	public String save(@RequestBody Map<String, Object> data,
						@AuthenticationPrincipal MemberDetail memberDetail,
						HttpSession session) {
		if(memberDetail == null) {
			List<CartResponseDto> list = (List<CartResponseDto>) session.getAttribute("cart");
			list = cartService.save(list, data);
			session.setAttribute("cart", list);
			return "비회원 장바구니 주문";
		}
		cartService.save(data, memberDetail.getMember());
		return "장바구니에 추가되었습니다.";
	}
	
	@ResponseBody
	@PutMapping(value="/cart/api")
	public CartResponseDto update(@RequestBody Map<String, Object> data, 
						@AuthenticationPrincipal MemberDetail memberDetail) {
		CartResponseDto dto = cartService.update(data);
		return dto;
	}
	
	@ResponseBody
	@DeleteMapping(value="/cart/api")
	public void delete(@RequestBody Map<String, Object> data, HttpSession session) {
		String productId = String.valueOf(data.get("productId"));
		String num = String.valueOf(data.get("num"));
		if(Integer.parseInt(num) == 0) {
			@SuppressWarnings("unchecked")
			List<CartResponseDto> list = (List<CartResponseDto>) session.getAttribute("cart");
			cartService.delete(list, productId);
		}else {
			cartService.delete(num);
		}
	}

}
