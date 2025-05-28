package com.sky.context;

public class EmployeeContext extends BaseContext {
    public static ThreadLocal<Long> employeeId = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        employeeId.set(id);
    }

    public static Long getCurrentId() {
        return employeeId.get();
    }

    public static void removeCurrentId() {
        employeeId.remove();
    }
}
