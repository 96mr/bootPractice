package com.spring.bootPractice.order.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spring.bootPractice.global.exception.CustomException;
import com.spring.bootPractice.global.exception.ErrorCode;
import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.order.dto.CartRequestDto;
import com.spring.bootPractice.order.dto.CartResponseDto;
import com.spring.bootPractice.order.dto.OrderItemDto;
import com.spring.bootPractice.order.entity.Cart;
import com.spring.bootPractice.order.repository.CartRepository;
import com.spring.bootPractice.product.dto.ImageResponseDto;
import com.spring.bootPractice.product.entity.Product;
import com.spring.bootPractice.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
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
			String imgUrl = "/bootPractice/attach/"+ image.getSave_name() + image.getExtension();
			json.addProperty("num", dto.getId());
			json.addProperty("name", dto.getProductId().getPname());
			json.addProperty("productId", dto.getProductId().getPid());
			json.addProperty("thumbnail", imgUrl);
			json.addProperty("count", dto.getCount());
			json.addProperty("price", dto.getProductId().getPrice());
			json.addProperty("totalPrice", dto.getCount() * dto.getProductId().getPrice());
			array.add(json);
		}	
		return array;
	}
	
	@Transactional
	public CartResponseDto save(Member member, OrderItemDto item) {
		log.info("장바구니 추가");
		Product product = productRepository.findByPid(item.getProductId())
							.orElseThrow(()->new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
		CartRequestDto dto = CartRequestDto.builder()
										.productId(product)
										.memberId(member)
										.count(item.getCount())
										.build();
		Cart cart = cartRepository.save(dto.toEntity());
		return new CartResponseDto(cart);
	}
	
	@Transactional
	public List<CartResponseDto> save(List<CartResponseDto> list, OrderItemDto item){	
		Product product = productRepository.findByPid(item.getProductId())
							.orElseThrow(()->new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
		CartRequestDto requestDto = CartRequestDto.builder()
										.productId(product)
										.count(item.getCount())
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
		if(!addChecked) list.add(new CartResponseDto(product, item.getCount()));					
		return list;
	}
	
	@Transactional
	public CartResponseDto update(OrderItemDto dto) {
		Cart cart = cartRepository.findById(dto.getNum())
							.orElseThrow(()-> new CustomException(ErrorCode.CART_NOT_FOUND));
		cart.update(dto.getCount());
		return new CartResponseDto(cart);
	}
	
	public List<CartResponseDto> update(List<CartResponseDto>list, OrderItemDto item) {
		int id = item.getProductId();
		for(Iterator<CartResponseDto> iterator = list.iterator(); iterator.hasNext();) {
			CartResponseDto c = iterator.next();
			if(c.getProductId().getPid() == id) {
				c.setCount(item.getCount());
				break;
			}
		}
		return list;
	}
	
	@Transactional
	public void delete(int id) {
		Cart cart = cartRepository.findById(id)
				.orElseThrow(()-> new CustomException(ErrorCode.CART_NOT_FOUND));
		cartRepository.deleteById(cart.getId());
	}
	
	public List<CartResponseDto> delete(List<CartResponseDto> list, int id) {
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
