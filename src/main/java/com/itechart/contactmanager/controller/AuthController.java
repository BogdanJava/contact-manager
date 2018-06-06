package com.itechart.contactmanager.controller;

import com.itechart.contactmanager.dao.UserDao;
import com.itechart.contactmanager.model.User;
import com.itechart.contactmanager.payload.ApiResponse;
import com.itechart.contactmanager.payload.LoginRequest;
import com.itechart.contactmanager.payload.SignUpRequest;
import com.itechart.contactmanager.payload.TokenAuthenticationResponse;
import com.itechart.contactmanager.security.CurrentUser;
import com.itechart.contactmanager.security.CustomUserDetails;
import com.itechart.contactmanager.service.TokenService;
import com.itechart.contactmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.generateToken(authentication);
        return ResponseEntity.ok(new TokenAuthenticationResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userDao.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "User with such username already exists!"));
        }

        User user = new User(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        User result = userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/users/{username}").buildAndExpand(result.getUsername()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@CurrentUser CustomUserDetails userDetails) {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok(new ApiResponse(true, "Successfully logged out"));
    }
}
