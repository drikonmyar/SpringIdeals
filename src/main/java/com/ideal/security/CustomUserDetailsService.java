package com.ideal.security;

import com.ideal.entity.UserEntity;
import com.ideal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User: " + username + " not found!!!"));

       return new User(
               user.getUsername(),
               user.getPassword(),
               List.of(new SimpleGrantedAuthority(user.getRole()))
       );
    }
}
