package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeEditPasswordQueryDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.service.EmployeeService;
import com.sky.vo.EmployeePageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    @Override
    public void editPassword(EmployeeEditPasswordQueryDTO employeeEditPasswordQueryDTO) {
        Long empId = employeeEditPasswordQueryDTO.getEmpId();
        String oldPassword = DigestUtils.md5DigestAsHex(employeeEditPasswordQueryDTO.getOldPassword().getBytes());
        Employee employee = employeeMapper.getEmployeeById(empId);
        if (employee == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        if (employee.getPassword() == null || !employee.getPassword().equals(oldPassword)) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        String newPassword = DigestUtils.md5DigestAsHex(employeeEditPasswordQueryDTO.getNewPassword().getBytes());
        employeeMapper.editPassword(empId, newPassword, oldPassword);
    }

    @Override
    public void optionEmployeeStatus(Integer status, Long id) {
        if (status.equals(StatusConstant.DISABLE) || status.equals(StatusConstant.ENABLE)) {
            Employee employee = employeeMapper.getEmployeeById(id);
            if (employee == null) {
                throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
            }
            employeeMapper.optionEmployeeStatus(status, id);
        }
        else {
            throw new RuntimeException("状态值错误");
        }
    }

    @Override
    public EmployeePageVO getEmployeeByPage(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        List<Employee> employeeList = employeeMapper.getEmployeeByPage(employeePageQueryDTO);
        Page<Employee> employeePage = (Page<Employee>) employeeList;
        return EmployeePageVO.builder()
                .total(employeePage.getTotal())
                .records(employeePage.getResult())
                .build();
    }

    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = Employee.builder()
                                    .username(employeeDTO.getUsername())
                                    .name(employeeDTO.getName())
                                    .phone(employeeDTO.getPhone())
                                    .sex(employeeDTO.getSex())
                                    .idNumber(employeeDTO.getIdNumber())
                                    .status(StatusConstant.ENABLE)
                                    .password(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()))
                                    .build();

        if (employeeDTO.getId() != null) {
            employee.setId(employeeDTO.getId());
        }
        employeeMapper.insertEmployee(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Employee employee = employeeMapper.getEmployeeById(id);
        return employee;
    }

    @Override
    public void updateEmployee(EmployeeDTO employeeDTO) {
        employeeMapper.updateEmployee(employeeDTO);
    }


}
