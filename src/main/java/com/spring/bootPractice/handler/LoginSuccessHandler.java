package com.spring.bootPractice.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	private String defaultUrl = "/";
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("로그인 성공");
		
		//이전 로그인 실패 시 발생한 세션 제거
		clearAuthenticationAttributes(request);
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		String prevPage = (String) request.getSession().getAttribute("prevPage");
		if(prevPage != null) request.getSession().removeAttribute("prevPage");

		if(savedRequest != null) {
			redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
		}else if(prevPage != null && prevPage.equals("")){
			if(prevPage.contains("/register")) {
				redirectStrategy.sendRedirect(request, response, request.getContextPath()+defaultUrl);
			}else
				redirectStrategy.sendRedirect(request, response, prevPage);
		}else
			redirectStrategy.sendRedirect(request, response, request.getContextPath()+defaultUrl);
	}
	
	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if( session != null )
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
