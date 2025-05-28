package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeEditPasswordQueryDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.vo.EmployeePageVO;

import java.math.BigInteger;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void editPassword(EmployeeEditPasswordQueryDTO employeeEditPasswordQueryDTO);

    void optionEmployeeStatus(Integer status, Long id);

    EmployeePageVO getEmployeeByPage(EmployeePageQueryDTO employeePageQueryDTO);

    void addEmployee(EmployeeDTO employeeDTO);

    Employee getEmployeeById(Long id);

    void updateEmployee(EmployeeDTO employeeDTO);
}
