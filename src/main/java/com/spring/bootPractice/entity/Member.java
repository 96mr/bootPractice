package com.spring.bootPractice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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
	@Column
	private String auth;
	
}
