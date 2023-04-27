package com.spring.bootPractice.service;

import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.spring.bootPractice.entity.MailDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MailService {

	private final JavaMailSender mailSender;
	
	//인증번호 메일 생성
	public MailDto createAuthKeyMail(HttpSession session, String address) {
		String authKey = createKey(6);
		MailDto dto = MailDto.builder()
				.address(address)
				.title("회원가입 인증 메일입니다.")
				.message(new StringBuffer().append("<h1>[이메일 인증 번호]</h1>")
						.append("해당 인증 번호를 입력해주세요.")
						.append("<h3>CODE : ")
						.append(authKey)
						.append("</h3>").toString()
						)
				.from("EmailTest")
				.build();
		System.out.println(authKey);
		session.setAttribute("emailAuthKey", authKey);
		return dto;
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
			System.out.println("메일 전송 완료");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
