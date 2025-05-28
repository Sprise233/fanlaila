package com.sky.mapper;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    void insertCategory(Category category);

    List<Category> getCategoryByType(CategoryPageQueryDTO categoryPageQueryDTO);

    void updateCategory(CategoryDTO categoryDTO, Long updateUserId);

    void optionCategoryStatus(Integer status, Long id);

    void deleteCategory(Long id);
}
