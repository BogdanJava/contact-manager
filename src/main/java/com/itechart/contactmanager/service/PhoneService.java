package com.itechart.contactmanager.service;

import com.itechart.contactmanager.model.Phone;
import com.itechart.contactmanager.payload.PhoneRequest;
import com.itechart.contactmanager.security.CustomUserDetails;

import java.util.List;

public interface PhoneService {
    Phone save(PhoneRequest phoneRequest, CustomUserDetails userDetails);
    List<Phone> getPhones(long employeeId, CustomUserDetails userDetails);
    Phone getPhone(long phoneId, CustomUserDetails userDetails);
}
