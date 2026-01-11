//package com.ideal.security;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.ApplicationContext;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class SecurityConfigTest {
//
//    @Autowired
//    private ApplicationContext context;
//
//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private SecurityFilterChain securityFilterChain;
//
//    @Test
//    void contextLoads() {
//        assertNotNull(context, "ApplicationContext should load");
//        assertNotNull(jwtUtils, "JwtUtils bean should be present");
//        assertNotNull(customUserDetailsService, "CustomUserDetailsService should be present");
//        assertNotNull(passwordEncoder, "PasswordEncoder should be present");
//        assertNotNull(authenticationManager, "AuthenticationManager should be present");
//        assertNotNull(securityFilterChain, "SecurityFilterChain should be present");
//    }
//
//    @Test
//    void passwordEncoderIsBCrypt() {
//        assertTrue(passwordEncoder instanceof org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder,
//                "PasswordEncoder should be BCryptPasswordEncoder");
//    }
//
//    @Test
//    void jwtUtilsGenerateAndValidateToken() {
//        String token = jwtUtils.generateToken("testuser");
//        assertNotNull(token, "Generated token should not be null");
//        assertTrue(jwtUtils.validateToken(token), "Generated token should be valid");
//        assertEquals("testuser", jwtUtils.extractUserName(token), "Username should match");
//    }
//}