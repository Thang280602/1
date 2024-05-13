package com.shop.shoes.service;

import com.shop.shoes.dto.CategoryDTO;
import com.shop.shoes.model.Category;


import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category findCategoryById(Long id);

    Category createCategory(CategoryDTO categoryDTO);

    Category updateCategory(Long id,CategoryDTO categoryDTO);

    void deleteCategoryById(Long id);
}
