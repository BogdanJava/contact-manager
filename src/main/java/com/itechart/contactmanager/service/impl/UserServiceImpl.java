package com.itechart.contactmanager.service.impl;

import com.itechart.contactmanager.dao.UserDao;
import com.itechart.contactmanager.exception.ResourceNotFoundException;
import com.itechart.contactmanager.exception.RestrictionException;
import com.itechart.contactmanager.model.User;
import com.itechart.contactmanager.security.CustomUserDetails;
import com.itechart.contactmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User save(User user) {
        userDao.save(user);
        return user;
    }

    @Override
    public User getByUsername(String username, CustomUserDetails userDetails) {
        User user;
        try {
            user = userDao.findByUsername(username);
            if (userDetails.getId() != user.getId()) {
                throw new RestrictionException("You are not allowed to get this resource.");
            }
            return user;
        } catch (NoResultException e) {
            throw new ResourceNotFoundException("User", "username", username);
        }
    }
}
