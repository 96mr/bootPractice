package com.spring.bootPractice.review.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.member.entity.MemberDetail;
import com.spring.bootPractice.review.dto.ReviewProductDto;
import com.spring.bootPractice.review.dto.ReviewRequestDto;
import com.spring.bootPractice.review.dto.ReviewResponseDto;
import com.spring.bootPractice.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;
	
	@ResponseBody
	@GetMapping(value="/api/review/{product}")
	public ResponseEntity<List<ReviewProductDto>> list(@PathVariable("product") int productId) {
		List<ReviewProductDto> list = reviewService.findByProductId(productId);
		if(list.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value="/member/review")
	public String list(@AuthenticationPrincipal MemberDetail memberDetail, Model model) {
		List<ReviewResponseDto> list = reviewService.findByMemberId(memberDetail.getMember());
		model.addAttribute("reviews", list);
		return "/member/review";
	}
	
	@GetMapping(value="/member/review/{orderId}")
	public String detail(@PathVariable("orderId") int orderId, Model model) {
		ReviewResponseDto review = reviewService.findByOrderId(orderId);
		model.addAttribute("review", review);
		return "/review/detail";
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping(value="/review")
	public String form(@RequestParam int orderId, 
						@AuthenticationPrincipal MemberDetail memberDetail, Model model) {
		Member member = memberDetail.getMember();
		boolean result = reviewService.equalsMemberId(member.getId(), orderId);
		if(!result) return "redirect:/member/order";
		model.addAttribute("orderId", orderId);
		model.addAttribute("review", new ReviewRequestDto());
		return "/review/form";
	}
	
	@PostMapping(value="/review")
	public String save(@Valid @ModelAttribute("review") ReviewRequestDto requestDto, BindingResult result,
						@AuthenticationPrincipal MemberDetail memberDetail, RedirectAttributes rttr) {
		if(result.hasErrors()) {
			return "/review/form";
		}
		Member memberId = memberDetail.getMember();
		requestDto.setMemberId(memberId);
		reviewService.save(requestDto);
		return "redirect:/member/reviews";
	}
}
