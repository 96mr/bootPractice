package com.spring.bootPractice.member.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.spring.bootPractice.member.entity.MemberDetail;

@Component
public class MemberAuthenticationProvider implements AuthenticationProvider {

	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	
	public MemberAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("provider");
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		MemberDetail member = (MemberDetail) userDetailsService.loadUserByUsername(username);

		if(!passwordEncoder.matches(password, member.getPassword())) {
			throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		
		if(!member.isEnabled()) {
			throw new BadCredentialsException("사용 불가능한 회원입니다.");
		}

		return new UsernamePasswordAuthenticationToken(member, password, member.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
