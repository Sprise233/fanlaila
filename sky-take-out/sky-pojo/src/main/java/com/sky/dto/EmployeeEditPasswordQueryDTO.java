package com.sky.dto;

import lombok.Data;

@Data
public class EmployeeEditPasswordQueryDTO {
    private Long empId;
    private String newPassword;
    private String oldPassword;
}
