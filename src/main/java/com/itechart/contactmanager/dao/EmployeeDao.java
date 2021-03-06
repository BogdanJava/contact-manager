package com.itechart.contactmanager.dao;

import com.itechart.contactmanager.model.Employee;

import java.util.List;

/**
 * @author Bogdan Shishkin
 * project: contact-manager
 * date/time: 05.06.2018 / 0:30
 * email: bogdanshishkin1998@gmail.com
 */

public interface EmployeeDao extends BaseDao<Employee> {
    List<Employee> getByUserId(long userId);
}
