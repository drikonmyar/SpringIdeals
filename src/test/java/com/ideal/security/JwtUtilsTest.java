package com.ideal.security;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;

    @BeforeEach
    void setup() {
        jwtUtils = new JwtUtils();
        // inject secret manually
        org.springframework.test.util.ReflectionTestUtils.setField(jwtUtils, "secret", "01234567890123456789012345678901"); // 32-byte key
        jwtUtils.init(); // initialize SecretKey
    }

    @Test
    void generateToken_and_extractUserName_success() {
        String username = "john";

        String token = jwtUtils.generateToken(username);
        assertNotNull(token);

        String extractedUsername = jwtUtils.extractUserName(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void validateToken_validToken_returnsTrue() {
        String username = "alice";
        String token = jwtUtils.generateToken(username);

        assertTrue(jwtUtils.validateToken(token));
    }

    @Test
    void validateToken_invalidToken_returnsFalse() {
        String invalidToken = "invalid.token.value";

        assertFalse(jwtUtils.validateToken(invalidToken));
    }

    @Test
    void extractUserName_invalidToken_throwsException() {
        String invalidToken = "invalid.token.value";

        assertThrows(JwtException.class, () -> jwtUtils.extractUserName(invalidToken));
    }
}