package com.itechart.contactmanager.dao.impl;

import com.itechart.contactmanager.dao.UserDao;
import com.itechart.contactmanager.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

/**
 * @author Bogdan Shishkin
 * project: contact-manager
 * date/time: 05.06.2018 / 0:32
 * email: bogdanshishkin1998@gmail.com
 */

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    {
        this.entityClass = User.class;
    }

    @Override
    public User findByUsername(String username) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", entityClass)
                .setParameter("username", username).getSingleResult();
    }

    @Override
    public boolean existsByUsername(String username) {
        try {
            findByUsername(username);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
