package com.spring.bootPractice.entity;

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
