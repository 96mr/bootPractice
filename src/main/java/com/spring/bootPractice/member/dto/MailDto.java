package com.spring.bootPractice.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MailDto {
	private String address;
	private String title;
	private String message;
	private String from;
}
