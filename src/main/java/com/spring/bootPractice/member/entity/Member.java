package com.spring.bootPractice.member.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@DynamicInsert
@Entity
@Table(name="MEMBER", schema="EX4")
public class Member {
	@Id
	private String id;
	@Column(nullable= false)
	private String password;
	@Column
	private String name;
	@Column(unique=true)
	private String email;
	@Temporal(TemporalType.DATE)
	private Date regdate;
	@Enumerated(EnumType.STRING)
	private Role auth;
	
	@Builder
	public Member(String id, String password, String name, String email, Date regdate, Role auth) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.regdate = regdate;
		this.auth = auth;
	}
	
	public void update(Role auth) {
		this.auth = auth;
	}
}
