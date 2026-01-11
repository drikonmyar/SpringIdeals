//package com.ideal.controller;
//
//import com.ideal.entity.UserEntity;
//import com.ideal.security.JwtUtils;
//import com.ideal.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Import;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.*;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//@WebMvcTest(
//        controllers = AuthController.class,
//        excludeFilters = {
//                @ComponentScan.Filter(
//                        type = FilterType.ASSIGNABLE_TYPE,
//                        classes = {
//                                com.ideal.security.SecurityConfig.class,
//                                com.ideal.security.JwtUtils.class
//                        }
//                )
//        }
//)
//@Import(AuthControllerTest.TestConfig.class)
//class AuthControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    // ---------- LOGIN ----------
//    @Test
//    void login_success() throws Exception {
//        mockMvc.perform(post("/auth/login")
//                        .param("username", "user")
//                        .param("password", "pass"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").value("dummy-token"));
//    }
//
//    // ---------- REGISTER ----------
//    @Test
//    void register_success() throws Exception {
//        String body = """
//                {
//                  "username": "user",
//                  "password": "pass"
//                }
//                """;
//
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value("user"));
//    }
//
//    // ---------- TEST BEANS ----------
//    static class TestConfig {
//
//        @Bean
//        JwtUtils jwtUtils() {
//            return new JwtUtils() {
//                @Override
//                public String generateToken(String username) {
//                    return "dummy-token";
//                }
//            };
//        }
//
//        @Bean
//        AuthenticationManager authenticationManager() {
//            return authentication ->
//                    new UsernamePasswordAuthenticationToken(
//                            authentication.getPrincipal(),
//                            authentication.getCredentials()
//                    );
//        }
//
//        @Bean
//        UserDetailsService userDetailsService() {
//            return username -> User
//                    .withUsername(username)
//                    .password("pass")
//                    .roles("USER")
//                    .build();
//        }
//
//        @Bean
//        UserService userService() {
//            return new UserService() {
//                @Override
//                public UserEntity registerUser(UserEntity user) {
//                    return user;
//                }
//
//                @Override
//                public UserEntity createAdmin(UserEntity user) {
//                    return user;
//                }
//            };
//        }
//    }
//}