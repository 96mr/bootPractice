package com.spring.bootPractice.member.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bootPractice.member.dto.MailDto;
import com.spring.bootPractice.member.dto.MailResponseDto;
import com.spring.bootPractice.member.entity.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MailService {

	private final JavaMailSender mailSender;
	
	//인증번호 메일 생성
	@Transactional
	public MailResponseDto createAuthKeyMail(Member member) {
		String authKey = createKey(6);
		
		MailDto mailDto = MailDto.builder()
				.address(member.getEmail())
				.title("회원가입 인증 메일입니다.")
				.message(new StringBuffer().append("<h1>[이메일 인증 번호]</h1>")
						.append("해당 인증 번호를 입력해주세요.")
						.append("<h3>CODE : ")
						.append(authKey)
						.append("</h3>").toString()
						)
				.from("EmailTest")
				.build();
		sendMail(mailDto);
		MailResponseDto mailResponseDto = new MailResponseDto(member.getId(), authKey); 
		return mailResponseDto;
	}
	
	//인증 번호 생성
	public String createKey(int size) {
		StringBuffer code = new StringBuffer();
		Random random = new Random();
		
		while(code.length() < size) {
			code.append(random.nextInt(10));
		}
		
		return code.toString();
	}
	
	//메일 전송
	public void sendMail(MailDto mailDto) {
		MimeMessage mailMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mailMessage, false, "utf-8");
			helper.setTo(mailDto.getAddress());
			helper.setSubject(mailDto.getTitle());
			helper.setText(mailDto.getMessage(), true); // true : html형식으로
			helper.setFrom(mailDto.getFrom());
			
			mailSender.send(mailMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}catch (MailException e) {
			e.printStackTrace();
		}
	}
	
}
