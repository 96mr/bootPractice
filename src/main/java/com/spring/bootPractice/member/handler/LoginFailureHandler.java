package com.spring.bootPractice.member.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class LoginFailureHandler implements AuthenticationFailureHandler {
	private final String DEFAULT_FAILURE_URL = "/login";

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		System.out.println("로그인 실패");
        String errormsg = null;
        
        if(exception instanceof BadCredentialsException) {
            errormsg = "아이디 또는 비밀번호가 일치하지 않습니다.";
        } else if(exception instanceof UsernameNotFoundException) {
        	errormsg = "아이디 또는 비밀번호가 일치하지 않습니다.";
        } else if(exception instanceof InternalAuthenticationServiceException) {
            errormsg = "시스템 오류가 발생했습니다.";
        } else if(exception instanceof DisabledException) {
            errormsg = "계정이 비활성화 되었습니다.";
        } else if(exception instanceof CredentialsExpiredException) {
            errormsg = "비밀번호 유효기간 만료되었습니다.";
        } else if(exception instanceof AccountExpiredException) {
        	errormsg = "계정이 만료되었습니다.";
        }
        
        request.setAttribute("id", request.getParameter("id"));
        request.setAttribute("errormsg", errormsg);
        request.getRequestDispatcher(DEFAULT_FAILURE_URL).forward(request, response);
	}
}
