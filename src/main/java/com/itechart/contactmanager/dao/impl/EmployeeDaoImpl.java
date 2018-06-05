package com.itechart.contactmanager.dao.impl;

import com.itechart.contactmanager.dao.EmployeeDao;
import com.itechart.contactmanager.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<Employee> getByUserId(long userId) {
        return entityManager.createQuery("SELECT e FROM Employee e WHERE e.user.id = :userId", entityClass)
                .setParameter("userId", userId).getResultList();
    }
}
