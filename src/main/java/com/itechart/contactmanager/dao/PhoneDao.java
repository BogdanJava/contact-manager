package com.itechart.contactmanager.dao;

import com.itechart.contactmanager.model.Phone;

/**
 * @author Bogdan Shishkin
 * project: contact-manager
 * date/time: 05.06.2018 / 0:30
 * email: bogdanshishkin1998@gmail.com
 */

public interface PhoneDao extends BaseDao<Phone> {
    Phone findByNumber(String number);
}