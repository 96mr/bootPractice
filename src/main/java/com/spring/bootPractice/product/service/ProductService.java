package com.spring.bootPractice.product.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.spring.bootPractice.global.common.FileUpload;
import com.spring.bootPractice.global.exception.CustomException;
import com.spring.bootPractice.global.exception.ErrorCode;
import com.spring.bootPractice.product.dto.ImageRequestDto;
import com.spring.bootPractice.product.dto.ProductRequestDto;
import com.spring.bootPractice.product.dto.ProductResponseDto;
import com.spring.bootPractice.product.entity.Category;
import com.spring.bootPractice.product.entity.ImageCategory;
import com.spring.bootPractice.product.entity.Product;
import com.spring.bootPractice.product.repository.CategoryRepository;
import com.spring.bootPractice.product.repository.ImageRepository;
import com.spring.bootPractice.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {
	
	@Value("${file.infoDir}")
	private String path;
	
	private final ProductRepository productRepository;
	private final ImageRepository imageRepository;
	private final CategoryRepository categoryRepository;
	private final FileUpload fileUpload;
	
	@Transactional
	public String save(ProductRequestDto productDto, List<MultipartFile> files) {
		Product product = productRepository.save(productDto.toEntity());
		List<ImageRequestDto> list = fileUpload.parseFileInfo(files, ImageCategory.PRODUCT, product.getPid());
		Iterator<ImageRequestDto> iterator = list.iterator();
		while(iterator.hasNext()) {
			ImageRequestDto image = iterator.next();
			imageRepository.save(image.toEntity());
		}
		return productDto.getPcategory().getName();
	}
	
	public Page<ProductResponseDto> productList(String category, Pageable pageable) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
		pageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());
		Page<Product> product;
		if(category.equals("main")) {
			product = productRepository.findAll(pageable);
		}else {		
			Category pcategory = categoryRepository.findByName(category)
					.orElseThrow(()-> new IllegalStateException("존재하지 않는 카테고리입니다."));
			product = (pcategory.getParent() == null) ? 
					productRepository.categoryByParent(pcategory, pageable):
					productRepository.findByPcategory(pcategory, pageable);
		}
		Page<ProductResponseDto> dtoList = product.map(p->new ProductResponseDto(p));
		
		return dtoList;
	}
	
	public ProductResponseDto getProductInfo(int pid) {
		Product product= productRepository.findByPid(pid)
							.orElseThrow(()-> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
		return new ProductResponseDto(product);
	}
	
	public ProductRequestDto parseProductRequestDto(ProductResponseDto dto) {
		Optional<Category> category = categoryRepository.findById(dto.getPcategory());
		ProductRequestDto requestDto = ProductRequestDto.builder()
													.pid(dto.getPid())
													.pname(dto.getPname())
													.pcategory(category.get())
													.pinfo(dto.getPinfo())
													.price(dto.getPrice())
													.status(dto.getStatus())
													.thumbnail(dto.getThumbnail())
													.build();
		return requestDto;
	}
	
	@Transactional
	public ProductResponseDto update(ProductRequestDto productDto) {
		Product product = productRepository.findByPid(productDto.getPid())
										.orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
		product.update(productDto.getPname(), productDto.getPcategory(), productDto.getPinfo()
						, productDto.getPrice(), productDto.getStatus());
		return new ProductResponseDto(product);
	}
	
	public JsonObject uploadSummerNoteImage(MultipartFile multipartFile) {
		JsonObject json = new JsonObject();
	
		String orgName = multipartFile.getOriginalFilename();
		String extension = orgName.substring(orgName.lastIndexOf("."));
		
		String savedFileName = UUID.randomUUID().toString().replaceAll("-", "") + extension;
		
		File target = new File(path);
		if(!target.exists()) {
			target.mkdirs();
		}
		
		target = new File(path, savedFileName); // filePath에 savedFileName 업로드
		
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, target);
			json.addProperty("url","/bootPractice/productInfo/"+ savedFileName);
			json.addProperty("responseCode", "success");
		}catch(IOException e) {
			FileUtils.deleteQuietly(target);
			json.addProperty("responseCode", "error");
			e.printStackTrace();
		}
				
		return json;
	}
	
}
