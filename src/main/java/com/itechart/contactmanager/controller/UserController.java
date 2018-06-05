package com.itechart.contactmanager.controller;

import com.itechart.contactmanager.dao.UserDao;
import com.itechart.contactmanager.payload.UserIdentityAvailability;
import com.itechart.contactmanager.payload.UserSummary;
import com.itechart.contactmanager.security.CurrentUser;
import com.itechart.contactmanager.security.CustomUserDetails;
import com.itechart.contactmanager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @GetMapping("/user/me")
    public UserSummary getCurrentUser(@CurrentUser CustomUserDetails userDetails) {
        return new UserSummary(userDetails.getId(), userDetails.getUsername());
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam String username) {
        return new UserIdentityAvailability(!userDao.existsByUsername(username));
    }

    @GetMapping("/users/{username}")
    public ResponseEntity getUser(@CurrentUser CustomUserDetails userDetails,
                                  @PathVariable String username) {
        return ResponseEntity.ok(userService.getByUsername(username, userDetails));
    }
}
