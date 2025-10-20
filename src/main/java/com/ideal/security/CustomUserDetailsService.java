package com.ideal.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username)) {
            return new User("admin", "{noop}admin123", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        } else if ("user".equals(username)) {
            return new User("user", "{noop}user123", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        }
        throw new UsernameNotFoundException("User not found");
    }
}
