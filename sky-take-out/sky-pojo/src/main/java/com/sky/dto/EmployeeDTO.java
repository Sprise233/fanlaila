package com.sky.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EmployeeDTO implements Serializable {

    private Long id;

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "姓名不能为空")
    private String name;

    @NotNull(message = "手机号不能为空")
    private String phone;

    @NotNull(message = "性别不能为空")
    private String sex;

    @NotNull(message = "身份证号不能为空")
    private String idNumber;

}
