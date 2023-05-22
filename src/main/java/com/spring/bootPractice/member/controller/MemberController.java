package com.spring.bootPractice.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.bootPractice.member.dto.MailResponseDto;
import com.spring.bootPractice.member.dto.MemberDto;
import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.member.entity.MemberDetail;
import com.spring.bootPractice.member.service.MailService;
import com.spring.bootPractice.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final MailService mailService;

	@RequestMapping(value="/login", method = {RequestMethod.GET, RequestMethod.POST})
	public String login(HttpServletRequest request, Authentication authentication) {
		System.out.println("login");
		System.out.println(authentication);
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
	public String register(Authentication authentication, Model model) {
		if(authentication != null) {
			return "/";
		}
		model.addAttribute("member", new MemberDto());
		return "register";
	}
	
	@PostMapping(value="/register")
	public String regitser(@Valid @ModelAttribute("member") MemberDto memberDto, BindingResult bindingResult, 
							HttpSession session, RedirectAttributes rttr, Model model) {
		if(bindingResult.hasErrors()) {
			return "register";
		}
		try {
			Member member = memberService.save(memberDto);
			MailResponseDto mailResponseDto = mailService.createAuthKeyMail(member);
			session.setAttribute("authMail", mailResponseDto);
		}catch(IllegalStateException e) {
			model.addAttribute("errorMsg", e.getMessage());
			return "register";
		}
		rttr.addFlashAttribute("msg", "해당 이메일로 인증번호가 전송되었습니다.");
		return "redirect:/register/confirm";
	}

	@GetMapping(value="/register/confirm")
	public String registerConfirm() {
		
		return "mailConfirm";
	}
	
	@PostMapping(value="/register/confirm")
	public String registerConfirm(@RequestParam String code, HttpSession session, RedirectAttributes rttr, Model model) {
		MailResponseDto mailResponseDto = (MailResponseDto) session.getAttribute("authMail");
		if(memberService.updateMemberAuth(mailResponseDto, code)) {
			
			//계정의 권한 정보 가져옴
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication != null)
				memberService.createNewAuthentication(authentication);
			session.invalidate();
			rttr.addFlashAttribute("msg", "인증되었습니다.");
			return "redirect:/";
		}else {
			model.addAttribute("errorMsg", "인증 번호를 다시 확인해주세요.");
			return "mailConfirm";
		}
	}

	@GetMapping(value="/member/page")
	public String memberDetail(@AuthenticationPrincipal MemberDetail memberDetail, Model model) {
		if(memberDetail != null) {
			model.addAttribute("member", memberDetail);
		}else {
			model.addAttribute("member", "존재하지 않습니다.");
		}
		return "member/detail";
	}
	
	@GetMapping(value="/denied")
	public String denied(Model model) {
		return "member/denied";
	}
	
}
