package com.itechart.contactmanager.service;

import com.itechart.contactmanager.model.User;
import com.itechart.contactmanager.security.CustomUserDetails;

public interface UserService {
    User save(User user);
    User getByUsername(String username, CustomUserDetails userDetails);
}
