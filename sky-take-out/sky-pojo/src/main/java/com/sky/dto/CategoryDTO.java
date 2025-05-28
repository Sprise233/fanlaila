package com.sky.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {

    //主键
    private Long id;

    //类型 1 菜品分类 2 套餐分类
    @NotNull(message = "分类类型不能为空")
    private Integer type;

    //分类名称
    @NotNull(message = "分类名称不能为空")
    private String name;

    //排序
    @NotNull(message = "分类排序不能为空")
    private Integer sort;

}
