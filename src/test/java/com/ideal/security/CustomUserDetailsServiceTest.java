package com.ideal.security;

import com.ideal.entity.UserEntity;
import com.ideal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    private UserRepository userRepository;
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setup() {
        userRepository = Mockito.mock(UserRepository.class);

        userDetailsService = new CustomUserDetailsService();
        // inject mock
        org.springframework.test.util.ReflectionTestUtils.setField(userDetailsService, "userRepository", userRepository);
    }

    @Test
    void loadUserByUsername_success() {
        UserEntity user = new UserEntity();
        user.setUsername("john");
        user.setPassword("encodedPass");
        user.setRole("ROLE_USER");

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername("john");

        assertEquals("john", userDetails.getUsername());
        assertEquals("encodedPass", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));

        verify(userRepository, times(1)).findByUsername("john");
    }

    @Test
    void loadUserByUsername_notFound_throwsException() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("unknown"));

        assertTrue(exception.getMessage().contains("unknown not found"));
        verify(userRepository, times(1)).findByUsername("unknown");
    }
}