package com.shop.shoes.service;

import com.shop.shoes.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAll();

    CategoryDTO findCategoryById(Long id);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategoryById(Long id);
}
