package com.ideal.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new TestExceptionController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void handleResourceNotFoundException() throws Exception {
        mockMvc.perform(get("/test/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Resource not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void handleNoChangeException() throws Exception {
        mockMvc.perform(get("/test/no-change"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("No change detected"));
    }

    @Test
    void handleInsufficientPermissionException() throws Exception {
        mockMvc.perform(get("/test/forbidden"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.message").value("Access denied"));
    }

    @Test
    void handleUserAlreadyExistsException() throws Exception {
        mockMvc.perform(get("/test/user-exists"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("User already exists"));
    }

    @Test
    void handleGenericException() throws Exception {
        mockMvc.perform(get("/test/generic"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Something went wrong"));
    }

    // ------------------ Dummy Controller ------------------

    @RestController
    static class TestExceptionController {

        @GetMapping("/test/not-found")
        public void throwNotFound() {
            throw new ResourceNotFoundException("Resource not found");
        }

        @GetMapping("/test/no-change")
        public void throwNoChange() {
            throw new NoChangeException("No change detected");
        }

        @GetMapping("/test/forbidden")
        public void throwForbidden() {
            throw new InsufficientPermissionException("Access denied");
        }

        @GetMapping("/test/user-exists")
        public void throwUserExists() {
            throw new UserAlreadyExists("User already exists");
        }

        @GetMapping("/test/generic")
        public void throwGeneric() {
            throw new RuntimeException("Something went wrong");
        }
    }
}
