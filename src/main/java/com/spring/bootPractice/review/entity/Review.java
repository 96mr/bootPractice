package com.spring.bootPractice.review.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;

import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.order.entity.OrderDetail;
import com.spring.bootPractice.product.entity.Product;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@DynamicInsert
@Entity
@Table(name="REVIEW", schema="EX4")
public class Review {
	@Id
	@Column(name ="order_id")
	private int orderId;
	
	@OneToOne
	@MapsId("order_id")
	@JoinColumn(name="order_id")
	private OrderDetail orderDetail;
	
	private String content;
	private int rating;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product_id")
	private Product productId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member memberId;
	@Temporal(TemporalType.TIMESTAMP)
	private Date regdate;
	
	@Builder
	public Review(int orderId, OrderDetail orderDetail, String content, int rating, Product productId, Member memberId) {
		this.orderId = orderId;
		this.orderDetail = orderDetail;
		this.content = content;
		this.rating = rating;
		this.productId = productId;
		this.memberId = memberId;
	}	

}
