package com.itechart.contactmanager.service.impl;

import com.itechart.contactmanager.dao.EmployeeDao;
import com.itechart.contactmanager.dao.PhoneDao;
import com.itechart.contactmanager.exception.RestrictionException;
import com.itechart.contactmanager.model.Employee;
import com.itechart.contactmanager.model.Phone;
import com.itechart.contactmanager.payload.PhoneRequest;
import com.itechart.contactmanager.security.CustomUserDetails;
import com.itechart.contactmanager.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Transactional
    @Override
    public Phone save(PhoneRequest phoneRequest, CustomUserDetails userDetails) {
        Employee employee = employeeDao.findOne(phoneRequest.getEmployeeId());
        if (employee.getUser().getId() != userDetails.getId()) {
            throw new RestrictionException("You are not allowed to add this resource.");
        }
        return phoneDao.save(new Phone(employee, phoneRequest.getNumber()));
    }

    @Override
    public List<Phone> getPhones(long employeeId, CustomUserDetails userDetails) {
        Employee employee = employeeDao.findOne(employeeId);
        if (employee.getUser().getId() != userDetails.getId()) {
            throw new RestrictionException("You are not allowed to get this resource.");
        }
        return phoneDao.getByEmployeeId(employeeId);
    }

    @Override
    public Phone getPhone(long phoneId, CustomUserDetails userDetails) {
        Phone phone = phoneDao.findOne(phoneId);
        if (phone.getEmployee().getUser().getId() != userDetails.getId()) {
            throw new RestrictionException("You are not allowed to get this resource.");
        }
        return phone;
    }
}
