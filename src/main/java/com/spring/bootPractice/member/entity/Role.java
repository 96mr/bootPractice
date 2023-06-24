package com.spring.bootPractice.member.entity;

import lombok.Getter;

@Getter
public enum Role {
	ROLE_USER("회원"),
	ROLE_GUEST("준회원"),
	ROLE_ADMIN("관리자");
	
	private String role;
	
	Role(String role){
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
	
}
