package com.spring.bootPractice.product.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonObject;
import com.spring.bootPractice.product.dto.ProductRequestDto;
import com.spring.bootPractice.product.dto.ProductResponseDto;
import com.spring.bootPractice.product.service.CategoryService;
import com.spring.bootPractice.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	private final CategoryService categoryService;
	
	@GetMapping(value="/products/list")
	public String read(@RequestParam(value="category", required= false) String category, Model model) {
		List<ProductResponseDto> list = productService.read(category);
		model.addAttribute("productList", list);
		model.addAttribute("category", categoryService.allList());
		return "/product/list";
	}
	
	@GetMapping(value="/products/{id}")
	public String productInfo(@PathVariable("id") int id, Model model) {
		ProductResponseDto product = productService.getProductInfo(id);
		model.addAttribute("info", product);
		return "/product/info";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value="/products")
	public String save(Model model) {
		model.addAttribute("product", new ProductRequestDto());
		model.addAttribute("category", categoryService.childrenList());
		return "product/create";
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping(value = "/products")
	public String save(@Valid @ModelAttribute("product") ProductRequestDto productDto, BindingResult result, 
						@RequestPart(value="thumbnail", required = false) List<MultipartFile> files,
						RedirectAttributes rttr, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("category", categoryService.childrenList());
			return "product/create";
		}
		String category = productService.save(productDto, files);
		if(category != null) {
			rttr.addFlashAttribute("msg", "상품이 추가되었습니다.");
			return "redirect:/products/list";
		}else {
			model.addAttribute("msg", "다시 시도해주세요.");
			return "/product/create";
		}
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping(value = "/product/uploadImage")
	@ResponseBody
	public JsonObject uploadImage(@RequestParam("file") MultipartFile multipartFile) {
		return productService.uploadImage(multipartFile);
	}
	
}
