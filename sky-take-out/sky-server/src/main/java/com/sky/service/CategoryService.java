package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

public interface CategoryService {
    void insertCategory(CategoryDTO categoryDTO);

    PageResult getCategoryByType(CategoryPageQueryDTO categoryPageQueryDTO);

    void updateCategory(CategoryDTO categoryDTO);

    void optionCategoryStatus(Integer status, Long id);

    void deleteCategory(Long id);
}
