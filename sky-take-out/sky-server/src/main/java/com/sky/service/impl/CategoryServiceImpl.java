package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.EmployeeContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void insertCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .id(categoryDTO.getId())
                .type(categoryDTO.getType())
                .name(categoryDTO.getName())
                .sort(categoryDTO.getSort())
                .status(StatusConstant.ENABLE)
                .createUser(EmployeeContext.getCurrentId())
                .updateUser(EmployeeContext.getCurrentId())
                .build();
        categoryMapper.insertCategory(category);
    }

    @Override
    public PageResult getCategoryByType(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        List<Category> categoryList =  categoryMapper.getCategoryByType(categoryPageQueryDTO);
        Page<Category> pages = (Page<Category>) categoryList;
        return new PageResult(pages.getTotal(),pages.getResult());
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Long updateUserId = EmployeeContext.getCurrentId();
        categoryMapper.updateCategory(categoryDTO, updateUserId);
    }

    @Override
    public void optionCategoryStatus(Integer status, Long id) {
        categoryMapper.optionCategoryStatus(status, id);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryMapper.deleteCategory(id);
    }
}
