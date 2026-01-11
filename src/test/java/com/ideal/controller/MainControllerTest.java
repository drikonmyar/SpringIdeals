//package com.ideal.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(MainController.class)
//class MainControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void getMethod_shouldReturnResponse_withNullBody() throws Exception {
//        mockMvc.perform(
//                        get("/main/get/John")
//                                .param("org", "Google")
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().string(
//                        "Hi John Good Day from Google You have null Laptops"
//                ));
//    }
//
//    @Test
//    void getMethod_withoutOrg_shouldWork() throws Exception {
//        mockMvc.perform(
//                        get("/main/get/John")
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().string(
//                        "Hi John Good Day from null You have null Laptops"
//                ));
//    }
//
//    @Test
//    void postMethod_withBody_shouldWork() throws Exception {
//        mockMvc.perform(
//                        post("/main/post/John")
//                                .param("org", "Amazon")
//                                .contentType("application/json")
//                                .content("10")
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().string(
//                        "POST METHOD: Hi John Good Day from Amazon You have 10 Laptops"
//                ));
//    }
//
//    @Test
//    void postMethod_withoutBody_shouldWork() throws Exception {
//        mockMvc.perform(
//                        post("/main/post/John")
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().string(
//                        "POST METHOD: Hi John Good Day from null You have null Laptops"
//                ));
//    }
//}