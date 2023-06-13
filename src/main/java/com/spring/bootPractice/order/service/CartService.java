package com.spring.bootPractice.order.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.order.dto.CartRequestDto;
import com.spring.bootPractice.order.dto.CartResponseDto;
import com.spring.bootPractice.order.entity.Cart;
import com.spring.bootPractice.order.repository.CartRepository;
import com.spring.bootPractice.product.dto.ImageResponseDto;
import com.spring.bootPractice.product.entity.Product;
import com.spring.bootPractice.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	
	@Transactional
	public List<CartResponseDto> list(Member memberId){
		List<Cart> list = cartRepository.findByMemberId(memberId);
		return list.stream().map(CartResponseDto::new).collect(Collectors.toList());
	}
	
	public JsonArray parseListJson(List<CartResponseDto> list) {
		JsonArray array = new JsonArray();	
		for(CartResponseDto dto : list) {
			JsonObject json = new JsonObject();
			ImageResponseDto image = new ImageResponseDto(dto.getProductId().getThumbnail().get(0));
			String imgUrl = image.getSave_name() + image.getExtension();
			json.addProperty("num", dto.getId());
			json.addProperty("productName", dto.getProductId().getPname());
			json.addProperty("productId", dto.getProductId().getPid());
			json.addProperty("thumbnail", imgUrl);
			json.addProperty("count", dto.getCount());		
			array.add(json);
		}	
		return array;
	}
	
	
	@Transactional
	public CartResponseDto save(Map<String, Object> data, Member member) {
		int pid = Integer.parseInt(String.valueOf(data.get("product")));
		int count = Integer.parseInt(String.valueOf(data.get("count")));
		Product product = productRepository.findByPid(pid)
							.orElseThrow(()->new IllegalStateException("존재하지 않는 상품입니다."));
		CartRequestDto dto = CartRequestDto.builder()
										.productId(product)
										.memberId(member)
										.count(count)
										.build();
		Cart cart = cartRepository.save(dto.toEntity());
		return new CartResponseDto(cart);
	}
	
	@Transactional
	public List<CartResponseDto> save(List<CartResponseDto> list, Map<String, Object> data){	
		int pid = Integer.parseInt(String.valueOf(data.get("product")));
		int count = Integer.parseInt(String.valueOf(data.get("count")));
		Product product = productRepository.findByPid(pid)
							.orElseThrow(()->new IllegalStateException("존재하지 않는 상품입니다."));
		CartRequestDto requestDto = CartRequestDto.builder()
										.productId(product)
										.count(count)
										.build();
		/* 세션에 저장되는 list에 추가
		 * 1. 장바구니 리스트가 비어있으면 생성
		 * 2. 추가하는 상품이 이미 장바구니에 존재하면 count만 변경
		 * 3. addChecked로 추가 여부 체크
		 */
		boolean addChecked = false;
		if(list == null) {
			list = new ArrayList<CartResponseDto>();
		}else {
			for(Iterator<CartResponseDto> iterator = list.iterator(); iterator.hasNext();) {
				CartResponseDto c = iterator.next();
				if(c.getProductId().getPid() == requestDto.getProductId().getPid()) {
					int sum = c.getCount() + requestDto.getCount();
					c.setCount(sum);
					addChecked = true;
				}
			}
		}
		if(!addChecked) list.add(new CartResponseDto(product, count));					
		return list;
	}
	
	@Transactional
	public CartResponseDto update(Map<String, Object> data) {
		int num = Integer.parseInt(String.valueOf(data.get("num")));
		int count = Integer.parseInt(String.valueOf(data.get("count")));
		Cart cart = cartRepository.findById(num)
							.orElseThrow(()-> new IllegalStateException("장바구니에 존재하지 않습니다."));
		cart.update(count);
		return new CartResponseDto(cart);
	}
	
	public void delete(String num) {
		int id = Integer.parseInt(num);
		cartRepository.deleteById(id);
	}
	
	public List<CartResponseDto> delete(List<CartResponseDto> list, String productId) {
		int id = Integer.parseInt(productId);
		for(Iterator<CartResponseDto> iterator = list.iterator(); iterator.hasNext();) {
			CartResponseDto c = iterator.next();
			if(c.getProductId().getPid() == id) {
				list.remove(c);
				break;
			}
		}
		return list;
	}
}
