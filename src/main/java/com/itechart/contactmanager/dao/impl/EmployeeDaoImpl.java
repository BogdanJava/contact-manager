package com.itechart.contactmanager.dao.impl;

import com.itechart.contactmanager.dao.EmployeeDao;
import com.itechart.contactmanager.model.Employee;
import org.springframework.stereotype.Repository;

/**
 * @author Bogdan Shishkin
 * project: contact-manager
 * date/time: 05.06.2018 / 0:31
 * email: bogdanshishkin1998@gmail.com
 */

@Repository
public class EmployeeDaoImpl extends BaseDaoImpl<Employee> implements EmployeeDao {
    {
        this.entityClass = Employee.class;
    }
}
