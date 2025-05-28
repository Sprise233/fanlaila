package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PageParamsNotFoundException;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/category")
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PutMapping
    public Result<Object> updateCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }

    @PostMapping
    public Result<Object> insertCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.insertCategory(categoryDTO);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<Object> getCategoryByParams(CategoryPageQueryDTO categoryPageQueryDTO){
        PageResult pageResult = categoryService.getCategoryByType(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    public Result<Object> optionCategoryStatus(@PathVariable Integer status, Long id){
        if (!status.equals(StatusConstant.DISABLE) && !status.equals(StatusConstant.ENABLE)) {
            throw new PageParamsNotFoundException("status是必填字段");
        }
        categoryService.optionCategoryStatus(status, id);
        return Result.success();
    }

    @DeleteMapping
    public Result<Object> deleteCategory(Long id){
        categoryService.deleteCategory(id);
        return Result.success();
    }


}
