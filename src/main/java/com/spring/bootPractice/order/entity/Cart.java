package com.spring.bootPractice.order.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.product.entity.Product;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@SequenceGenerator(name="CART_GEN_SEQ", sequenceName="CART_SEQ", allocationSize=1)
@DynamicInsert
@Table(name="CART", schema="EX4")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CART_GEN_SEQ")
	private int id;
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="product_id")
	private Product productId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member memberId;
	private int count;
	
	@Builder
	public Cart(int id, Product productId, Member memberId, int count) {
		this.id = id;
		this.productId = productId;
		this.memberId = memberId;
		this.count = count;
	}
	
	public void update(int count) {
		this.count = count;
	}
}
