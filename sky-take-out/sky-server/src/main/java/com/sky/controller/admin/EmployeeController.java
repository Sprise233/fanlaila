package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeEditPasswordQueryDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import com.sky.vo.EmployeePageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        log.info("员工退出");
        return Result.success();
    }


    @PutMapping("/editPassword")
    public Result<Object> editPassword(@RequestBody EmployeeEditPasswordQueryDTO employeeEditPasswordQueryDTO) {
        Long empId = employeeEditPasswordQueryDTO.getEmpId();
        log.info("修改{}员工密码:{}", empId, employeeEditPasswordQueryDTO);
        employeeService.editPassword(employeeEditPasswordQueryDTO);
        return Result.success();
    }


    @PostMapping("/status/{status}")
    public  Result<Object> optionEmployeeStatus(@PathVariable Integer status,
                                                @RequestParam("id") Long id){
        log.info("id为{}的员工状态修改为：{}",id, status);
        employeeService.optionEmployeeStatus(status, id);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<EmployeePageVO> getEmployeeByPage(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("分页查询员工信息：{}", employeePageQueryDTO);
        EmployeePageVO employeePageVO = employeeService.getEmployeeByPage(employeePageQueryDTO);
        return Result.success(employeePageVO);
    }

    @PostMapping
    public Result<Object> insertEmployee(@RequestBody EmployeeDTO employeeDTO){
        log.info("添加员工：{}", employeeDTO);
        employeeService.addEmployee(employeeDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Employee> getEmployeeById(@PathVariable Long id){
        log.info("查询员工id为{}的员工信息", id);
        Employee employee = employeeService.getEmployeeById(id);
        return Result.success(employee);
    }

    @PutMapping
    public Result<Object> updateEmployee(@RequestBody EmployeeDTO employeeDTO){
        log.info("修改员工信息：{}", employeeDTO);
        employeeService.updateEmployee(employeeDTO);
        return Result.success();
    }

}
