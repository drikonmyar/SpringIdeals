package com.ideal.service;

import com.ideal.entity.UserEntity;
import com.ideal.exception.InsufficientPermissionException;
import com.ideal.exception.UserAlreadyExists;
import com.ideal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity registerUser(UserEntity user){
        Optional<UserEntity> existingUser = userRepository.findByUsername(user.getUsername());
        if(existingUser.isPresent()){
            throw new UserAlreadyExists("User with name: " + user.getUsername() + " already exists!!");
        }
        if(user.getRole().equals("ROLE_ADMIN")){
            throw new InsufficientPermissionException("ROLE_ADMIN can only be assigned from create-admin API");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public UserEntity createAdmin(UserEntity user){
        Optional<UserEntity> existingUser = userRepository.findByUsername(user.getUsername());
        if(existingUser.isPresent()){
            throw new UserAlreadyExists("User with name: " + user.getUsername() + " already exists!!");
        }
        user.setRole("ROLE_ADMIN");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
