package com.itechart.contactmanager.dao;

import com.itechart.contactmanager.model.Phone;

import java.util.List;

/**
 * @author Bogdan Shishkin
 * project: contact-manager
 * date/time: 05.06.2018 / 0:30
 * email: bogdanshishkin1998@gmail.com
 */

public interface PhoneDao extends BaseDao<Phone> {
    Phone findByNumber(String number);
    List<Phone> getByEmployeeId(long employeeId);
    boolean exists(String number);
}
