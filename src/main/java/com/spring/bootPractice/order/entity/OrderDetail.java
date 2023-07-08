package com.spring.bootPractice.order.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.spring.bootPractice.product.entity.Product;
import com.spring.bootPractice.review.entity.Review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@SequenceGenerator(name="ORDER_DETAIL_GEN_SEQ", sequenceName="ORDER_DETAIL_SEQ", allocationSize=1)
@Entity
@DynamicInsert
@Table(name="ORDER_DETAIL", schema="EX4")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ORDER_DETAIL_GEN_SEQ")
	private int id;
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order orderId;
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="product_id")
	private Product productId;
	private int count;
	private int price;
	@Enumerated(EnumType.STRING)
	private Status status; // 배송 상태 enum으로 만들기
	@OneToOne(mappedBy = "orderDetail", fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Review review;
	
	@Builder
	public OrderDetail(Order orderId, Product productId, int count, int price, Status status) {
		this.orderId = orderId;
		this.productId = productId;
		this.count = count;
		this.price = price;
		this.status = status;
	}
	
	public void update(Status status) {
		this.status = status;
	}
	
}
