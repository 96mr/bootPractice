package com.spring.bootPractice.member.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bootPractice.member.dto.MailResponseDto;
import com.spring.bootPractice.member.dto.MemberDto;
import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.member.entity.Role;
import com.spring.bootPractice.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	
	private final MemberDetailService memberDetailService;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public Member save(MemberDto memberDto) {
		duplicateId(memberDto.getId());
		duplicateEmail(memberDto.getEmail());
	
		Member member = memberDto.toEntity(passwordEncoder);
		return memberRepository.save(member);
	}
	
	public void duplicateId(String id) {
		memberRepository.findById(id)
						.ifPresent(m-> {
							throw new IllegalStateException("이미 가입된 회원입니다.");
						});
	}
	
	public void duplicateEmail(String email) {
		memberRepository.findByEmail(email)
						.ifPresent(m-> {
							throw new IllegalStateException("이미 가입된 이메일입니다.");
						});
	}
	
	public boolean updateMemberAuth(MailResponseDto dto, String formData) {
		String authKey = dto.getKey();
		if(authKey.equals(formData)) {
			String id = dto.getId();
			Member member = memberRepository.findById(id)
											.orElseThrow(()-> new IllegalStateException("존재하지 않는 아이디"));
			member.update(Role.ROLE_USER);
			memberRepository.save(member);
			return true;
		}
		return false;
	}
	
	public void createNewAuthentication(Authentication authentication) {
		UserDetails newPrincipal = memberDetailService.loadUserByUsername(authentication.getName());
		UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(newPrincipal
																		,authentication.getCredentials()
																		,newPrincipal.getAuthorities());
		newAuth.setDetails(authentication.getDetails());
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}
	
}
