package com.ideal.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsufficientPermissionExceptionTest {

    @Test
    void constructor_setsMessageCorrectly() {
        String message = "Access denied";

        InsufficientPermissionException exception =
                new InsufficientPermissionException(message);

        assertEquals(message, exception.getMessage());
    }
}