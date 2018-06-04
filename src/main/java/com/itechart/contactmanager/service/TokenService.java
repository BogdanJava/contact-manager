package com.itechart.contactmanager.service;

import org.springframework.security.core.Authentication;

/**
 * @author Bogdan Shishkin
 * project: contact-manager
 * date/time: 05.06.2018 / 1:03
 * email: bogdanshishkin1998@gmail.com
 */

public interface TokenService {
    String generateToken(Authentication authentication);
    boolean validateToken(String token);
    long getUserIdFromToken(String token);
}
