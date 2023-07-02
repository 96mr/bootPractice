package com.spring.bootPractice.order.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bootPractice.global.exception.CustomException;
import com.spring.bootPractice.global.exception.ErrorCode;
import com.spring.bootPractice.order.dto.OrderDetailRequestDto;
import com.spring.bootPractice.order.dto.OrderItemDto;
import com.spring.bootPractice.order.dto.OrderPageDto;
import com.spring.bootPractice.order.dto.OrderRequestDto;
import com.spring.bootPractice.order.entity.Order;
import com.spring.bootPractice.order.entity.Status;
import com.spring.bootPractice.order.repository.CartRepository;
import com.spring.bootPractice.order.repository.OrderDetailRepository;
import com.spring.bootPractice.order.repository.OrderRepository;
import com.spring.bootPractice.product.entity.Image;
import com.spring.bootPractice.product.entity.Product;
import com.spring.bootPractice.product.entity.ProductStatus;
import com.spring.bootPractice.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final ProductRepository productRepository;
	private final CartRepository cartRepository;
	
	public String createOrderId() {
		LocalDate date = LocalDate.now();
		String orderId = date.format(DateTimeFormatter.ofPattern("yyMMdd"));
	
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
			
		while(sb.length() < 4) {
			sb.append(random.nextInt(10));
		}		
		orderId += sb.toString();
		return orderId;
	}
	
	@Transactional
	public OrderPageDto save(OrderPageDto dto, String memberId, boolean prevCartCheck) {	
		String id = createOrderId();
		while(true) {
			Optional<Order> order= orderRepository.findById(id);
			if(!order.isPresent()) break;
			id = createOrderId();
		}
		
		if(memberId.equals("guest")) {
			memberId += id;
		}
		
		OrderRequestDto orderDto = OrderRequestDto.builder()
												.id(id)
												.memberId(memberId)
												.receiverName(dto.getReceiverName())
												.receiverPhone(dto.getReceiverPhone())
												.address1(dto.getPostcode())
												.address2(dto.getAddress())
												.address3(dto.getDetailAddress())
												.build();
		Order order = orderRepository.save(orderDto.toEntity());

		Iterator<OrderItemDto> iterator = dto.getItems().iterator();
		while(iterator.hasNext()) {
			OrderItemDto item = iterator.next();
			int pid = item.getProductId();
			Product product = productRepository.findByPid(pid)
								.orElseThrow(()-> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
			
			if(product.getStatus() == ProductStatus.SOLD_OUT 
				|| product.getStatus() == ProductStatus.DISCONTINUED) {
				iterator.remove();
				continue;
			}
			OrderDetailRequestDto orderDetailDto = OrderDetailRequestDto.builder()
													.orderId(order)
													.productId(product)
													.price(item.getTotalPrice())
													.count(item.getCount())
													.status(Status.Processed)
													.build();
			orderDetailRepository.save(orderDetailDto.toEntity());
			if(memberId != null && prevCartCheck) cartRepository.deleteByMemberIdAndProductId(memberId, product);
		}
		
		dto.setOrderId(order.getId());
		return dto;
	}
	/*
	public List<OrderDetailResponseDto> list(int id){
		
	}*/
	
	public OrderItemDto gerOrderItem(OrderItemDto dto){
		  int id = dto.getProductId(); 
		  int count = dto.getCount(); 
		  Product product = productRepository.findByPid(id) 
				  						.orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
		  
		  Image img = product.getThumbnail().get(0); 
		  OrderItemDto result = OrderItemDto.builder() 
				  						.productId(id) 
				  						.count(count) 
				  						.name(product.getPname())
				  						.thumbnail("/bootPractice/attach/"+ img.getSave_name()+img.getExtension())
				  						.totalPrice(product.getPrice() * count) 
				  						.build();
		 		
		  return result; 
	}
}
