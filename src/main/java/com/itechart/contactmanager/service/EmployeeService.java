package com.itechart.contactmanager.service;

import com.itechart.contactmanager.model.Employee;
import com.itechart.contactmanager.security.CustomUserDetails;

public interface EmployeeService {
    Employee getById(long employeeId, CustomUserDetails userDetails);
}
