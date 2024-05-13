package com.shop.shoes.constant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CategoryConstant {
    public static final String CATEGORY_NOT_FOUND = "Không tìm thấy danh mục";
    public static final String CATEGORY_ALREADY_EXITS = "Danh muc đã tồn tại";
}
