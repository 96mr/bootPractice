package com.spring.bootPractice.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import com.spring.bootPractice.order.dto.OrderItemDto;
import com.spring.bootPractice.order.dto.OrderPageDto;
import com.spring.bootPractice.order.dto.OrderResponseDto;
import com.spring.bootPractice.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;

	@GetMapping(value="/cart")
	public String cart() {
		return "/order/cart";
	}
	
	@GetMapping(value="/order")
	public String list(@AuthenticationPrincipal MemberDetail memberDetail) {
		if(memberDetail == null) {
			return "/order/list";
		}else {
			return "forward:/member/order";
		}
	}

	@GetMapping(value="/member/order")
	public String list(@AuthenticationPrincipal MemberDetail memberDetail, Model model) {
		Member member = memberDetail.getMember();
		List<OrderResponseDto> list = orderService.getOrderList(member.getId());
		model.addAttribute("list", list);
		return "/member/order-list";
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping(value="/order/{orderId}")
	public String list(@PathVariable("orderId") String orderId, Model model) {
		model.addAttribute("orderId", orderId);
		return "/order/list";
	}
	
	@ResponseBody
	@GetMapping(value="/api/order/{orderId}")
	public ResponseEntity<OrderPageDto> list(@PathVariable("orderId") String orderId,
										@RequestParam(value="receiverName", required = false) String receiverName,
										@AuthenticationPrincipal MemberDetail memberDetail){
		OrderPageDto order = orderService.findByOrder(orderId);
		if(memberDetail != null) {
			Member member = memberDetail.getMember();
			if(!order.getMemberId().equals(member.getId())) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}else {
			if(!receiverName.equals(order.getReceiverName())) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		return ResponseEntity.ok().body(order);
	}
	
	@PostMapping(value="/order/form")
	public String orderForm(OrderItemDto dto, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String uri = request.getHeader("Referer");
	    session.setAttribute("prevPage", uri);
	    
		OrderItemDto item = orderService.gerOrderItem(dto);
		model.addAttribute("item", item);
		model.addAttribute("order", new OrderPageDto());
		return "/order/form";
	}
	
	@GetMapping(value="/order/form")
	public String orderForm(Model model) {
		model.addAttribute("order", new OrderPageDto());
		return "/order/form";
	}
	
	@PostMapping(value = "/order")
	public String save(@Valid @ModelAttribute("order") OrderPageDto order, BindingResult result, 
						@AuthenticationPrincipal MemberDetail memberDetail, HttpServletRequest request,
						RedirectAttributes rttr, Model model) {		
		HttpSession session = request.getSession();
		String uri = (String) session.getAttribute("prevPage");
		boolean prevCartCheck = (uri!= null && uri.contains("product"))? false : true;
		if(result.hasErrors()) {
			if(!prevCartCheck) {
				model.addAttribute("item", order.getItems().get(0));
			}
			return "/order/form";
		}
		
		String memberId = "guest";
		if(memberDetail != null) {
			Member member = memberDetail.getMember();
			memberId = member.getId();
		}
		OrderPageDto orderPageDto = orderService.save(order, memberId, prevCartCheck);
		
		if(prevCartCheck)
			session.removeAttribute("cart");
		else
			session.removeAttribute("prevPage");
		
		rttr.addFlashAttribute("order", orderPageDto);
		return "redirect:/order/confirm";
	}

	@GetMapping(value="/order/confirm")
	public String orderConfirm(Model model) {
		return "/order/confirm";
	}
}
