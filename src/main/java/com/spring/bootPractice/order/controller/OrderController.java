package com.spring.bootPractice.order.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.bootPractice.product.dto.ProductRequestDto;
import com.spring.bootPractice.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	
	private final ProductService productService;

	@GetMapping(value="/cart")
	public String cart() {
		return "/order/cart";
	}
	
	@GetMapping(value="/orders")
	public String list(Model model) {
		return "/order/list";
	}
	
	@GetMapping(value="/order")
	public String orderForm(HttpServletRequest request, Model model) {
		String productNum = request.getParameter("productId");
		String count = request.getParameter("count");
		productService.getProductInfo(Integer.parseInt(productNum));
		return "/order/form";
	}
	
	@PostMapping(value = "/order")
	public String save(@Valid @ModelAttribute("order") ProductRequestDto dto, 
						BindingResult result, RedirectAttributes rttr, Model model) {
		if(result.hasErrors()) {
			return "/order/form";
		}
		if(dto != null) {
			rttr.addFlashAttribute("msg", "주문이 완료되었습니다.");
			return "redirect:/index";
		}else {
			model.addAttribute("msg", "다시 시도해주세요.");
			return "/order/form";
		}
	}

}
