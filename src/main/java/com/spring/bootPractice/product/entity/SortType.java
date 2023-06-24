package com.spring.bootPractice.product.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortType {
	REGDATE_DESC("최신순"),
	PRICE_ASC("가격 낮은순"),
	PRICE_DESC("가격 높은순"),
	TOTAL_SALES("판매량순");
	private final String description;
}
