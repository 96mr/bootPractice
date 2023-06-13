package com.spring.bootPractice.order.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.spring.bootPractice.product.entity.Product;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Entity
@Table(name="ORDER_DETAIL", schema="EX4")
public class OrderDetail {
	@Id
	private int id;
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="order_id")
	private Order orderId;
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="product_id")
	private Product productId;
	private int count;
	private int price;
	private String status; // 배송 상태 enum으로 만들기
	
	@Builder
	public OrderDetail(Order orderId, Product productId, int count, int price, String status) {
		this.orderId = orderId;
		this.productId = productId;
		this.count = count;
		this.price = price;
		this.status = status;
	}
	
}
