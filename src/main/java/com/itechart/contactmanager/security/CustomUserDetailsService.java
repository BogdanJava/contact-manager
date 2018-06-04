package com.itechart.contactmanager.security;

import com.itechart.contactmanager.dao.UserDao;
import com.itechart.contactmanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Bogdan Shishkin
 * project: contact-manager
 * date/time: 05.06.2018 / 0:44
 * email: bogdanshishkin1998@gmail.com
 */

@Primary
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findByUsername(s);
        if(user == null) throw new UsernameNotFoundException("No user with such name: " + s);
        return CustomUserDetails.create(user);
    }
}
