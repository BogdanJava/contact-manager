package com.itechart.contactmanager.service;

import com.itechart.contactmanager.model.Employee;
import com.itechart.contactmanager.security.CustomUserDetails;

public interface EmployeeService {
    Employee getById(long employeeId, CustomUserDetails userDetails);
    Employee save(Employee employee);
    Employee update(Employee employee, CustomUserDetails userDetails);
    void delete(long employeeId, CustomUserDetails userDetails);
}
