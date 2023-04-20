package com.spring.bootPractice.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.bootPractice.entity.Member;
import com.spring.bootPractice.service.MemberDetailService;

@Controller
public class MemberController {
	
	private final MemberDetailService memberService;
	
	public MemberController(MemberDetailService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping(value="/")
	public String index() {
		return "index";
	}

	@RequestMapping(value="/login", method = {RequestMethod.GET, RequestMethod.POST})
	public String login(HttpServletRequest request, Authentication authentication) {
		System.out.println("login");

		if(authentication != null) {
			return "redirect:/";
		}
		String uri = request.getHeader("Referer");
	    if (uri != null && !uri.contains("/login")) {
	        request.getSession().setAttribute("prevPage", uri);
	    }
		return "login";
	}
	
	@GetMapping(value="/register")
	public String register(Authentication authentication) {
		if(authentication != null) {
			return "/";
		}
		return "register";
	}
	
	@PostMapping(value="/register")
	public String regitser(@Valid Member member, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "register";
		}
		
		Member result = memberService.save(member);
		return "redirect:/";
	}
	
	@GetMapping(value="/member/page")
	public String memberDetail(Authentication authentication) {
		
		return "member/detail";
	}
	
}
