package com.itechart.contactmanager.service.impl;

import com.itechart.contactmanager.dao.EmployeeDao;
import com.itechart.contactmanager.exception.RestrictionException;
import com.itechart.contactmanager.model.Employee;
import com.itechart.contactmanager.security.CustomUserDetails;
import com.itechart.contactmanager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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

    @Override
    public Employee save(Employee employee) {
        return employeeDao.save(employee);
    }

    @Override
    public Employee update(Employee employee, CustomUserDetails userDetails) {
        if(employee.getUser().getId() != userDetails.getId()) {
            throw new RestrictionException("You are not allowed to update this user.");
        }
        return this.employeeDao.update(employee);
    }

    @Override
    public void delete(long employeeId, CustomUserDetails userDetails) {
        Employee employee = employeeDao.findOne(employeeId);
        if(employee.getUser().getId() != userDetails.getId()) {
            throw new RestrictionException("You are not allowed to delete this user.");
        }
        this.employeeDao.delete(employee);
    }
}
