package com.spring.bootPractice.member.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@SequenceGenerator(name="ADDRESS_GEN_SEQ", sequenceName="ADDRESS_SEQ", allocationSize=1)
@Entity
@Table(name="ADDRESS", schema="EX4")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ADDRESS_GEN_SEQ")
	private int id;
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member memberId;
	private String address1;
	private String address2;
	private String address3;
	
	@Builder
	private Address(Member memberId, String address1, String address2, String address3) {
		this.memberId = memberId;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
	}
	
	public void update(String address1, String address2, String address3) {
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
	}
}
