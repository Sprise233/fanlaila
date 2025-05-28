package com.sky.dto;

import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EmployeePageQueryDTO implements Serializable {

    //员工姓名
    private String name;

    //页码
    @NotNull(message = "page不能为空")
    private Integer page;

    //每页显示记录数
    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;

}
