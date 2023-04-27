package com.spring.bootPractice.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.bootPractice.entity.MailDto;
import com.spring.bootPractice.entity.MemberDto;
import com.spring.bootPractice.service.MailService;
import com.spring.bootPractice.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final MailService mailService;
	
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
	public String register(Authentication authentication, Model model) {
		if(authentication != null) {
			return "/";
		}
		model.addAttribute("member", new MemberDto());
		return "register";
	}
	
	@PostMapping(value="/register")
	public String regitser(@Valid @ModelAttribute("member") MemberDto member, BindingResult bindingResult, 
							HttpServletRequest request, RedirectAttributes rttr, Model model) {
		if(bindingResult.hasErrors()) {
			return "register";
		}
		try {
			memberService.save(member);
			MailDto mailDto = mailService.createAuthKeyMail(request.getSession(), member.getEmail());
			mailService.sendMail(mailDto);	
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
	public String registerConfirm(HttpServletRequest request, RedirectAttributes rttr, Model model) {
		HttpSession session = request.getSession();
		String inputKey = request.getParameter("num");
		String authKey = (String) session.getAttribute("emailAuthKey");
		
		if(authKey.equals(inputKey) ) {
			session.removeAttribute("emailAuthKey");
			rttr.addFlashAttribute("msg", "인증되었습니다. 로그인 후 서비스 이용이 가능합니다.");
			return "redirect:/login";
		}else {
			model.addAttribute("errorMsg", "인증 번호를 다시 확인해주세요.");
			return "mailConfirm";
		}
	}
	
	@GetMapping(value="/member/page")
	public String memberDetail(Authentication authentication) {
		
		return "member/detail";
	}
	
}
