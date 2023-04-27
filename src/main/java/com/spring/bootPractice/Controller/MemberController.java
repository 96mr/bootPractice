package com.spring.bootPractice.Controller;

import javax.servlet.http.HttpServletRequest;
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

import com.spring.bootPractice.entity.MemberDTO;
import com.spring.bootPractice.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
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
		model.addAttribute("member", new MemberDTO());
		return "register";
	}
	
	@PostMapping(value="/register")
	public String regitser(@Valid @ModelAttribute("member") MemberDTO member, BindingResult bindingResult, 
							RedirectAttributes rttr, Model model) {
		if(bindingResult.hasErrors()) {
			return "register";
		}
		try {
			memberService.save(member);
		}catch(IllegalStateException e) {
			model.addAttribute("errorMsg", e.getMessage());
			return "register";
		}
		rttr.addFlashAttribute("msg", "성공적으로 회원가입 되었습니다.");
		return "redirect:/";
	}
	
	@GetMapping(value="/member/page")
	public String memberDetail(Authentication authentication) {
		
		return "member/detail";
	}
	
}
