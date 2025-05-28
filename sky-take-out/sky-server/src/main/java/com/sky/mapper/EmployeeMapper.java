package com.sky.mapper;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    void editPassword(Long empId, String passwordMD5, String oldPassword);

    @Select("select * from employee where id = #{empId}")
    Employee getEmployeeById(Long empId);

    void optionEmployeeStatus(Integer status, Long id);

    List<Employee> getEmployeeByPage(EmployeePageQueryDTO employeePageQueryDTO);

    void insertEmployee(Employee employee);

    void updateEmployee(EmployeeDTO employeeDTO);
}
