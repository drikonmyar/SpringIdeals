package com.ideal.service;

import com.ideal.entity.UserEntity;
import com.ideal.exception.InsufficientPermissionException;
import com.ideal.exception.UserAlreadyExists;
import com.ideal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);

        userService = new UserService();
        // inject mocks
        org.springframework.test.util.ReflectionTestUtils.setField(userService, "userRepository", userRepository);
        org.springframework.test.util.ReflectionTestUtils.setField(userService, "passwordEncoder", passwordEncoder);
    }

    @Test
    void registerUser_success() {
        UserEntity user = new UserEntity();
        user.setUsername("john");
        user.setPassword("pass");
        user.setRole("ROLE_USER");

        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass")).thenReturn("encodedPass");
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserEntity savedUser = userService.registerUser(user);

        assertEquals("encodedPass", savedUser.getPassword());
        assertEquals("john", savedUser.getUsername());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void registerUser_userAlreadyExists_throwsException() {
        UserEntity user = new UserEntity();
        user.setUsername("john");

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(new UserEntity()));

        UserAlreadyExists exception = assertThrows(UserAlreadyExists.class, () -> userService.registerUser(user));
        assertTrue(exception.getMessage().contains("already exists"));
    }

    @Test
    void registerUser_roleAdmin_throwsInsufficientPermission() {
        UserEntity user = new UserEntity();
        user.setUsername("john");
        user.setRole("ROLE_ADMIN");

        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());

        InsufficientPermissionException exception = assertThrows(InsufficientPermissionException.class,
                () -> userService.registerUser(user));
        assertTrue(exception.getMessage().contains("create-admin"));
    }

    @Test
    void createAdmin_success() {
        UserEntity user = new UserEntity();
        user.setUsername("adminUser");
        user.setPassword("adminPass");

        when(userRepository.findByUsername("adminUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("adminPass")).thenReturn("encodedAdminPass");
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserEntity savedAdmin = userService.createAdmin(user);

        assertEquals("encodedAdminPass", savedAdmin.getPassword());
        assertEquals("ROLE_ADMIN", savedAdmin.getRole());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void createAdmin_userAlreadyExists_throwsException() {
        UserEntity user = new UserEntity();
        user.setUsername("adminUser");

        when(userRepository.findByUsername("adminUser")).thenReturn(Optional.of(new UserEntity()));

        UserAlreadyExists exception = assertThrows(UserAlreadyExists.class, () -> userService.createAdmin(user));
        assertTrue(exception.getMessage().contains("already exists"));
    }
}