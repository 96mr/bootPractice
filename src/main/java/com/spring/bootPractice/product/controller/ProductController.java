package com.spring.bootPractice.product.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
	
	@GetMapping(value= {"/", "index"})
	public String index(@PageableDefault(page = 0, size = 4, sort ="hit", direction = Sort.Direction.DESC ) Pageable pageable,
						Model model) {
		Page<ProductResponseDto> list = productService.productList("main", pageable);
		model.addAttribute("productList", list);
		model.addAttribute("categoryList", categoryService.getAllList());
		return "index";
	}
	
	@GetMapping(value= {"/products/{category}","/products/{category}/{subCategory}"})
	public String categoryList(@PathVariable("category") String category, 
					   @PathVariable(value="subCategory",required=false) Optional<String> subCategory, 
					   @PageableDefault(page = 0, size = 8, sort ="regdate", direction = Sort.Direction.DESC) Pageable pageable,
					   Model model) {
		Page<ProductResponseDto> list;
		if(subCategory.isPresent()) {
			list = productService.productList(subCategory.get(), pageable);
			model.addAttribute("curSubCategory", subCategory.get());
		}else {
			list = productService.productList(category, pageable);
		}
		model.addAttribute("productList", list)
			 .addAttribute("categoryList", categoryService.getAllList())
			 .addAttribute("curCategory", category);	
		return "product/list";
	}
	
	@GetMapping(value="/product/{id}")
	public String productInfo(@PathVariable("id") int id, Model model) {
		ProductResponseDto product = productService.getProductInfo(id);
		model.addAttribute("info", product);
		return "product/info";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value="/product")
	public String save(Model model) {
		model.addAttribute("product", new ProductRequestDto());
		model.addAttribute("category", categoryService.getChildrenList());
		return "product/create";
	}
	
	@PostMapping(value = "/product")
	public String save(@Valid @ModelAttribute("product") ProductRequestDto productDto, BindingResult result, 
						@RequestPart(value="thumbnail", required = false) List<MultipartFile> files,
						RedirectAttributes rttr, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("category", categoryService.getChildrenList());
			return "product/create";
		}
		String category = productService.save(productDto, files);
		if(category != null) {
			rttr.addFlashAttribute("msg", "상품이 추가되었습니다.");
			return "redirect:/index";
		}else {
			model.addAttribute("msg", "다시 시도해주세요.");
			return "product/create";
		}
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping(value = "/product/uploadImage")
	@ResponseBody
	public JsonObject uploadSummerNoteImage(@RequestParam("file") MultipartFile multipartFile) {
		return productService.uploadSummerNoteImage(multipartFile);
	}
	
}
