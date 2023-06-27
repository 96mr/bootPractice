package com.spring.bootPractice.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;

import com.spring.bootPractice.member.entity.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@DynamicInsert
@Table(name="PRODUCT_ORDER", schema="EX4")
public class Order {
	@Id
	private int id;
	private String memberId;
	@Column(name="receiver_name")
	private String receiverName;
	@Column(name="receiver_phone")
	private String receiverPhone;
	@Temporal(TemporalType.DATE)
	private Date order_date;
	private String address1;
	private String address2;
	private String address3;
	
	@Builder
	public Order(String memberId, String receiverName, String receiverPhone, String address1, String address2, String address3) {
		this.memberId = memberId;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
	}
}
