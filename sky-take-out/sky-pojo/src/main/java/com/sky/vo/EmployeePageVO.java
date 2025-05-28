package com.sky.vo;

import com.sky.entity.Employee;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EmployeePageVO {
    private Long total;
    private List<Employee> records;


}
