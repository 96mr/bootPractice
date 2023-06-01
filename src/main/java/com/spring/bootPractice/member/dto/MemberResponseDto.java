package com.spring.bootPractice.member.dto;

import java.util.Date;

import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.member.entity.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {
	private String id;
	private String password;
	private String name;
	private String email;
	private Date regdate;
	private Role auth;

	public MemberResponseDto(Member member) {
		this.id = member.getId();
		this.password = member.getPassword();
		this.name = member.getName();
		this.email = member.getEmail();
		this.regdate = member.getRegdate();
		this.auth = member.getAuth();
	}
}
