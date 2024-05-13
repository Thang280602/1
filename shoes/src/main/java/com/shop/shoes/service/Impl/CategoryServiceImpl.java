package com.shop.shoes.service.Impl;

import com.shop.shoes.constant.CategoryConstant;
import com.shop.shoes.dto.CategoryDTO;
import com.shop.shoes.exception.domain.CategoryNotFoundException;
import com.shop.shoes.exception.domain.ExceptionMessage;
import com.shop.shoes.model.Category;
import com.shop.shoes.repository.CategoryRepository;
import com.shop.shoes.service.CategoryService;
import com.shop.shoes.util.CategoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final CategoryUtils categoryUtils;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryUtils categoryUtils) {
        this.categoryRepository = categoryRepository;
        this.categoryUtils = categoryUtils;
    }


    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public Category findCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(CategoryConstant.CATEGORY_NOT_FOUND));
        return category;
    }


    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        if (categoryDTO.getId() != null) {
            throw new IllegalArgumentException("Cannot create a category with a predefined ID.");
        }

        Category category = categoryUtils.mapCategoryDtoToCategory(categoryDTO);

        try {
            Category savedCategory = categoryRepository.save(category);
            return savedCategory;
        } catch (DataIntegrityViolationException e) {
            throw new CategoryNotFoundException(CategoryConstant.CATEGORY_ALREADY_EXITS);
        }
    }

    @Override
    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        if (id != categoryDTO.getId()) {
            throw new CategoryNotFoundException(CategoryConstant.CATEGORY_NOT_FOUND);
        }
        Category category = categoryUtils.mapCategoryDtoToCategory(categoryDTO);

        try {
            Category savedCategory = categoryRepository.save(category);
            return savedCategory;
        } catch (DataIntegrityViolationException e) {
            throw new CategoryNotFoundException(CategoryConstant.CATEGORY_ALREADY_EXITS);
        }
    }

    @Override
    public void deleteCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            throw new CategoryNotFoundException(CategoryConstant.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(id);
    }

}
