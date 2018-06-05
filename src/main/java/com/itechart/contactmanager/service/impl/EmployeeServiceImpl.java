package com.itechart.contactmanager.service.impl;

import com.itechart.contactmanager.dao.EmployeeDao;
import com.itechart.contactmanager.exception.RestrictionException;
import com.itechart.contactmanager.model.Employee;
import com.itechart.contactmanager.security.CustomUserDetails;
import com.itechart.contactmanager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public Employee getById(long employeeId, CustomUserDetails userDetails) {
        Employee employee = employeeDao.findOne(employeeId);
        if (employee.getUser().getId() != userDetails.getId()) {
            throw new RestrictionException("You are not allowed to get this resource.");
        }
        return employee;
    }
}
