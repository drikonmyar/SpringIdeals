package com.ideal.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserAlreadyExistsTest {

    @Test
    void constructor_setsMessageCorrectly() {
        String message = "User already exists";

        UserAlreadyExists exception = new UserAlreadyExists(message);

        assertEquals(message, exception.getMessage());
    }
}