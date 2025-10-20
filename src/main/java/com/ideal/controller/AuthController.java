package com.ideal.controller;

import com.ideal.entity.UserEntity;
import com.ideal.security.JwtUtils;
import com.ideal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails user = userDetailsService.loadUserByUsername(username);
        String token = jwtUtils.generateToken(user.getUsername());
        return Map.of("token", token);
    }

    @PostMapping("/register")
    public UserEntity register(@RequestBody UserEntity user){
        return userService.registerUser(user);
    }
}
