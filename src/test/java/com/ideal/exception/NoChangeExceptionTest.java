package com.ideal.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoChangeExceptionTest {

    @Test
    void constructor_setsMessageCorrectly() {
        String message = "No changes detected";

        NoChangeException exception = new NoChangeException(message);

        assertEquals(message, exception.getMessage());
    }
}