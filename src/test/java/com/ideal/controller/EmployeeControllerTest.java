package com.ideal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideal.dto.EmployeeDto;
import com.ideal.entity.Employee;
import com.ideal.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmployeeControllerTest {

    private MockMvc mockMvc;
    private EmployeeService employeeService;

    @BeforeEach
    void setup() {
        employeeService = Mockito.mock(EmployeeService.class);

        EmployeeController controller = new EmployeeController();
        // manually inject dependency
        ReflectionTestUtils.setField(controller, "employeeService", employeeService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        // mock security context
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "admin",
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                )
        );
    }

    @Test
    void createEmployee_success() throws Exception {
        Mockito.when(employeeService.createEmployee(Mockito.any()))
                .thenReturn(new Employee());

        mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new EmployeeDto())))
                .andExpect(status().isCreated());
    }

    @Test
    void getEmployee_success() throws Exception {
        Mockito.when(employeeService.getEmployee(1))
                .thenReturn(new Employee());

        mockMvc.perform(get("/employee/get/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllEmployees_success() throws Exception {
        Mockito.when(employeeService.getAllEmployees())
                .thenReturn(List.of());

        mockMvc.perform(get("/employee/getall"))
                .andExpect(status().isOk());
    }

    @Test
    void updateEmployee_success() throws Exception {
        Mockito.when(employeeService.updateEmployee(Mockito.eq(1), Mockito.any()))
                .thenReturn(new Employee());

        mockMvc.perform(put("/employee/update")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new EmployeeDto())))
                .andExpect(status().isOk());
    }

    @Test
    void uploadExcel_emptyFile_badRequest() throws Exception {
        mockMvc.perform(multipart("/employee/upload")
                        .file("file", new byte[0]))
                .andExpect(status().isBadRequest());
    }

    @Test
    void uploadExcel_exception_internalServerError() throws Exception {

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "employees.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "dummy data".getBytes()
        );

        Mockito.doThrow(new RuntimeException("Excel error"))
                .when(employeeService)
                .saveEmployeesFromExcel(Mockito.any());

        mockMvc.perform(multipart("/employee/upload")
                        .file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(
                        org.hamcrest.Matchers.containsString("Error uploading file")
                ));
    }
}