package com.shop.shoes.util;

import com.shop.shoes.dto.CategoryDTO;
import com.shop.shoes.model.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryUtils {
    private final ModelMapper modelMapper;
    public Category mapCategoryDtoToCategory(CategoryDTO categoryDTO){
        return modelMapper.map(categoryDTO, Category.class);
    }
    public CategoryDTO mapCategorytoCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }
}
